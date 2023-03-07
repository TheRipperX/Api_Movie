package com.example.filmapp.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.filmapp.R
import com.example.filmapp.databinding.FragmentSearchBinding
import com.example.filmapp.utils.ConnectivityStatus
import com.example.filmapp.utils.initItemMe
import com.example.filmapp.utils.initToastMe
import com.example.filmapp.viewmodels.SearchViewModels
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    @Inject
    lateinit var adapterSearchMovies: AdapterSearchMovies

    private val searchViewModels: SearchViewModels by viewModels()

    private val connectivityStatus: ConnectivityStatus by lazy {
        ConnectivityStatus(requireContext())
    }

    private val toastText = "please check internet"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {

            connectivityStatus.observe(viewLifecycleOwner) {

                if (it) {
                    //edit text
                    binding.edtSearchItems.addTextChangedListener { text ->

                        if (text.toString().isNotEmpty()) {
                            searchViewModels.searchMoviesList(text.toString())
                        }
                    }
                } else {
                    initToastMe(requireContext(), toastText)
                }
            }
            main()

        } catch (_: Exception) {
            initToastMe(requireContext(), toastText)
        }

    }

    private fun main() {

        binding.apply {



            // fun requests search
            searchViewModels.searchLiveData.observe(viewLifecycleOwner) {

                adapterSearchMovies.setDataSearch(it.data)

                recSearchItem.initItemMe(adapterSearchMovies, LinearLayoutManager(requireContext()))
            }

            // loading
            searchViewModels.loading.observe(viewLifecycleOwner) {

                if (it) {
                    recSearchItem.visibility = View.GONE
                    progressCircularSearch.visibility = View.VISIBLE
                } else {
                    progressCircularSearch.visibility = View.GONE
                    recSearchItem.visibility = View.VISIBLE
                }
            }

            //click
            adapterSearchMovies.setOnClickMovie {
                val directions = SearchFragmentDirections.actionsToDetail(it.id!!)
                findNavController().navigate(directions)
            }

            // empty list
            searchViewModels.emptyList.observe(viewLifecycleOwner) {

                if (it) {

                    recEmptyItems.visibility = View.VISIBLE
                    recSearchItem.visibility = View.GONE
                } else {

                    recEmptyItems.visibility = View.GONE
                    recSearchItem.visibility = View.VISIBLE
                }
            }
        }

    }

}