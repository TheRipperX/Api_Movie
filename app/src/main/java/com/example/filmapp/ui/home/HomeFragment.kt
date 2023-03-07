package com.example.filmapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.filmapp.databinding.FragmentHomeBinding
import com.example.filmapp.ui.home.adapter.AdapterGenresHome
import com.example.filmapp.ui.home.adapter.AdapterHomeTopMovie
import com.example.filmapp.ui.home.adapter.AdapterLastMovies
import com.example.filmapp.utils.ConnectivityStatus
import com.example.filmapp.utils.initItemMe
import com.example.filmapp.utils.initToastMe
import com.example.filmapp.viewmodels.HomeViewModels
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var adapterHomeTopMovie: AdapterHomeTopMovie

    @Inject
    lateinit var adapterGenresHome: AdapterGenresHome

    @Inject
    lateinit var adapterLastMovies: AdapterLastMovies

    private val toastText = "please check internet"

    private val viewModelHome: HomeViewModels by viewModels()

    private val connectivity: ConnectivityStatus by lazy {
        ConnectivityStatus(requireContext())
    }


    private val snapHelper: PagerSnapHelper by lazy { PagerSnapHelper() }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {

            connectivity.observe(viewLifecycleOwner) { isConnected ->

                if(isConnected){

                    //top movie items
                    val intRandom = (1..5).random()
                    viewModelHome.homeTopMovieLists(id = intRandom)

                    //genres items
                    viewModelHome.homeGenresList()

                    //load last movies
                    val pageCounter = (1..20).random()
                    viewModelHome.homeLastMoves(page = pageCounter)



                }else{

                    initToastMe(requireContext(), toastText)
                }

                main()
            }


        } catch (_: Exception) {
            initToastMe(requireContext(), toastText)
        }

        /*try {
            main()
        } catch (_: Exception) {
            initToastMe(requireContext(), toastText)
        }*/

    }

    @SuppressLint("SetTextI18n")
    private fun main() {

        binding.apply {

            //top movie items
            viewModelHome.homeTopMovieLiveData.observe(viewLifecycleOwner) {

                adapterHomeTopMovie.differ.submitList(it.data)

                //home top items
                recTopMovieHome.initItemMe(
                    adapterHomeTopMovie,
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                )

                snapHelper.attachToRecyclerView(recTopMovieHome)
                indicatorTopMovieHome.attachToRecyclerView(recTopMovieHome, snapHelper)
            }

            //click
            adapterLastMovies.setOnClickMovie {
                val directions = HomeFragmentDirections.actionsToDetail(it.id!!)
                findNavController().navigate(directions)
            }

            //genres items

            viewModelHome.homeGenresLiveData.observe(viewLifecycleOwner) {

                adapterGenresHome.differ.submitList(it)

                textViewGenres.text = "Genres"

                recGenresItems.initItemMe(
                    adapterGenresHome,
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
                )
            }

            // progress live data
            viewModelHome.progressLiveData.observe(viewLifecycleOwner) {
                if (it) {
                    nestedScrollView3.visibility = View.GONE
                    progressCircular.visibility = View.VISIBLE
                } else {
                    nestedScrollView3.visibility = View.VISIBLE
                    progressCircular.visibility = View.GONE
                }
            }

            // load last movies
            viewModelHome.homeLastMoviesLiveData.observe(viewLifecycleOwner) {

                txtLastmovie.text = "Last Movie"
                adapterLastMovies.getDataItems(it.data)

                recBot.initItemMe(adapterLastMovies, LinearLayoutManager(requireContext()))
            }
        }
    }

}