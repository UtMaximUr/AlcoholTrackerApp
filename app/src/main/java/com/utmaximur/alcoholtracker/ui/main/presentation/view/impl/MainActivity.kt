package com.utmaximur.alcoholtracker.ui.main.presentation.view.impl

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.model.AlcoholTrack
import com.utmaximur.alcoholtracker.ui.add.addtrack.presentation.view.impl.AddFragment


class MainActivity : AppCompatActivity(),
    AddFragment.AddFragmentListener {

    private lateinit var menu: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_AlcoholTracker)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUi()
    }

    private fun findViewById() {
        menu = findViewById(R.id.bottom_navigation_view)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    private fun initUi() {
        findViewById()
        navController = navHostFragment.navController
        menu.setupWithNavController(navController)
    }

    override fun closeFragment() {
        navController.popBackStack()
    }

    fun showAddAlcoholTrackerFragment() {
        navController.navigate(R.id.addFragment)
    }

    fun showEditAlcoholTrackerFragment(drink: AlcoholTrack?) {
        val bundle = Bundle()
        bundle.putParcelable("drink", drink)
        navController.navigate(R.id.addFragment, bundle)
    }

    override fun onHideNavigationBar() {
        menu.visibility = GONE
    }

    override fun onShowNavigationBar() {
        menu.visibility = VISIBLE
    }
}