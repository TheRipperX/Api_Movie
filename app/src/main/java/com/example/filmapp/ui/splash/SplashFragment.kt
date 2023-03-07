package com.example.filmapp.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedDispatcher
import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.example.filmapp.R
import com.example.filmapp.databinding.FragmentSplashBinding
import com.example.filmapp.utils.ConnectivityStatus
import com.example.filmapp.utils.DataStoreUserSave
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {

    private lateinit var binding: FragmentSplashBinding

    @Inject
    lateinit var dataStoreUserSave: DataStoreUserSave

    //internet
    private val connectivityStatus: ConnectivityStatus by lazy {
        ConnectivityStatus(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {

            connectivityStatus.observe(viewLifecycleOwner) { connect ->

                if (connect) {

                    imgSplash.setImageResource(R.drawable.videoplayer)
                    txtSplash.text = resources.getString(R.string.appName)
                    txtSplash.visibility = View.VISIBLE
                    txtNoInternet.visibility = View.GONE
                    progressCircularSplash.visibility = View.GONE

                    lifecycle.coroutineScope.launchWhenCreated {
                        delay(2500)

                        dataStoreUserSave.getUserToken().collect{

                            if (it.isEmpty()) {
                                findNavController().navigate(R.id.action_splashFragment_to_registerFragment)
                            }else {
                                findNavController().navigate(R.id.actions_to_home)
                            }
                        }
                    }

                }else {
                    imgSplash.setImageResource(R.drawable.nointernet)
                    txtSplash.visibility = View.GONE
                    txtNoInternet.visibility = View.VISIBLE
                    progressCircularSplash.visibility = View.VISIBLE
                }
            }

        }
    }

}