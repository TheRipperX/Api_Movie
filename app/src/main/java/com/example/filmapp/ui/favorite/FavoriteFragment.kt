package com.example.filmapp.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.R
import com.example.filmapp.databinding.FragmentFavoriteBinding
import com.example.filmapp.utils.ConnectivityStatus
import com.example.filmapp.utils.initItemMe
import com.example.filmapp.utils.initToastMe
import com.example.filmapp.viewmodels.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding

    @Inject
    lateinit var adapterFavorite: AdapterFavorite

    private val viewModelFav: FavoriteViewModel by viewModels()

    private val toastText = "please check internet"

    private val connectivityStatus: ConnectivityStatus by lazy {
        ConnectivityStatus(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {
            /*connectivityStatus.observe(viewLifecycleOwner) {
                if (it) {
                    //item fun get all data
                    viewModelFav.getFavItems()

                } else {
                    initToastMe(requireContext(), toastText)
                }
            }*/
            //item fun get all data
            viewModelFav.getFavItems()
            main()

        } catch (_: Exception) {
            initToastMe(requireContext(), toastText)
        }

    }

    private fun main() {

        binding.apply {

            viewModelFav.favItemLiveData.observe(viewLifecycleOwner) {

                adapterFavorite.getDataItems(it)

                recFavItem.initItemMe(adapterFavorite, LinearLayoutManager(requireContext()))
            }

            //click
            adapterFavorite.setOnClickMovie {
                val directions = FavoriteFragmentDirections.actionsToDetail(it.id)
                findNavController().navigate(directions)
            }

            //empty list
            viewModelFav.empty.observe(viewLifecycleOwner) {

                if (it) {
                    recEmptyFav.visibility = View.GONE
                    recFavItem.visibility = View.VISIBLE
                } else {
                    recEmptyFav.visibility = View.VISIBLE
                    recFavItem.visibility = View.GONE
                }
            }
        }

    }


}