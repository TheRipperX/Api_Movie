package com.example.filmapp.ui.detail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.example.filmapp.R
import com.example.filmapp.databinding.FragmentDetailsBinding
import com.example.filmapp.db.MoviesEntity
import com.example.filmapp.utils.ConnectivityStatus
import com.example.filmapp.utils.InternetCheckClass
import com.example.filmapp.utils.initItemMe
import com.example.filmapp.utils.initToastMe
import com.example.filmapp.viewmodels.DetailViewModels
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    @Inject
    lateinit var adapterDetail: AdapterDetail

    @Inject
    lateinit var moviesEntity: MoviesEntity

    private val toastText = "please check internet"

    private var movieId = 0
    private val detailViewModels: DetailViewModels by viewModels()
    private val args: DetailsFragmentArgs by navArgs()

    private val connectivityStatus: ConnectivityStatus by lazy {
        ConnectivityStatus(requireContext())
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("FragmentLiveDataObserve")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        try {

            //back btn
            binding.imgBack.setOnClickListener {
                findNavController().popBackStack()
            }

            connectivityStatus.observe(viewLifecycleOwner) {

                if (it) {
                    movieId = args.movieId
                    if (movieId > 0)
                        detailViewModels.detailMovie(movieId)

                    main()

                }else {
                    initToastMe(requireContext(), toastText)
                }
            }



        }catch (_: Exception) {
            initToastMe(requireContext(), toastText)
        }

    }


    private fun main() {

        binding.apply {


            detailViewModels.detailMovieData.observe(viewLifecycleOwner) {

                //click fav
                imgFav.setOnClickListener { _ ->

                    moviesEntity = MoviesEntity(movieId,
                        it.poster.toString(),
                        it.title.toString(),
                        it.imdbRating.toString(),
                        it.country.toString(),
                        it.year.toString()
                    )

                    detailViewModels.favMovie(movieId, moviesEntity)
                }

                //image
                imgBigPoster.load(it.poster)
                imgNormalPoster.load(it.poster)

                //text
                txtTitleVideo.text = it.title
                txtDetailStars.text = it.imdbRating
                txtTimeStars.text = it.runtime
                txtYearStars.text = it.year

                //summery
                txtSummaryApi.text = it.plot
                //actors
                txtActorsApi.text = it.actors

                try {
                    //images rec load
                    if (it.images!!.isNotEmpty()) {

                        adapterDetail.differ.submitList(it.images)

                        recActorsImage.initItemMe(
                            adapterDetail,
                            LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.HORIZONTAL,
                                false
                            )
                        )
                    }
                } catch (_: Exception) {

                }
            }

            //is fav bool
            lifecycle.coroutineScope.launchWhenCreated {
                val bool = detailViewModels.existMove(movieId)
                if (bool) {
                    imgFav.setImageResource(R.drawable.ic_baseline_favorite_24)
                } else {
                    imgFav.setImageResource(R.drawable.ic_baseline_favorite_border_24_empty)
                }
            }

            //if fav click
            detailViewModels.isFav.observe(viewLifecycleOwner) {
                if (it) {
                    imgFav.setImageResource(R.drawable.ic_baseline_favorite_24)
                } else {
                    imgFav.setImageResource(R.drawable.ic_baseline_favorite_border_24_empty)
                }
            }

            //loading
            detailViewModels.loading.observe(viewLifecycleOwner) {

                if (it) {
                    nestedScrollView.visibility = View.GONE
                    progressCircular.visibility = View.VISIBLE
                } else {
                    nestedScrollView.visibility = View.VISIBLE
                    progressCircular.visibility = View.GONE
                }
            }
        }
    }
}