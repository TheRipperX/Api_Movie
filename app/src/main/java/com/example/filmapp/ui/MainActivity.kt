package com.example.filmapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.filmapp.R
import com.example.filmapp.databinding.ActivityMainBinding
import com.example.filmapp.utils.initToastMe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //binding
    private lateinit var binding: ActivityMainBinding

    //other
    private lateinit var navController: NavController

    private val timeExit = 2000L
    private var times = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        main()
    }

    private fun main() {

        binding.apply {

            //hi
            navController = findNavController(R.id.navHost)

            bottomNavMain.setupWithNavController(navController)

            navController.addOnDestinationChangedListener {_, destination, _ ->

                when(destination.id) {

                    R.id.splashFragment -> bottomNavMain.visibility = View.GONE
                    R.id.registerFragment -> bottomNavMain.visibility = View.GONE
                    R.id.detailsFragment -> bottomNavMain.visibility = View.GONE

                    else -> bottomNavMain.visibility = View.VISIBLE
                }
            }
        }

    }

    override fun onBackPressed() {

        if (times + timeExit >= System.currentTimeMillis()){

            super.onBackPressed()
            navController.addOnDestinationChangedListener{_, destination, _ ->

                if (destination.id == R.id.splashFragment){
                    finishAffinity()
                }
            }
        }else {
            //System.currentTimeMillis()
            times = System.currentTimeMillis()
            initToastMe(this, "Double Click")
        }
    }

    override fun onNavigateUp(): Boolean {
        return navController.navigateUp() || super.onNavigateUp()
    }


}