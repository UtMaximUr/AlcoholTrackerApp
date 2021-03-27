package com.utmaximur.alcoholtracker.ui.main

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.ui.add.AddFragment
import com.utmaximur.alcoholtracker.ui.addmydrink.AddNewDrink
import com.utmaximur.alcoholtracker.ui.calendar.CalendarFragment


class MainActivity : AppCompatActivity(),
    AddFragment.AddFragmentListener,
    AddNewDrink.AddNewFragmentListener,
    CalendarFragment.CalendarFragmentListener {

    private lateinit var menu: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
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

    override fun showAddAlcoholTrackerFragment(bundle: Bundle?) {
        navController.navigate(R.id.addFragment, bundle)
    }

    override fun showEditAlcoholTrackerFragment(bundle: Bundle) {
        navController.navigate(R.id.addFragment, bundle)
    }

    override fun onHideNavigationBar() {
        menu.visibility = GONE
    }

    override fun onShowNavigationBar() {
        menu.visibility = VISIBLE
    }

    override fun onShowAddNewDrinkFragment() {
        navController.navigate(R.id.addNewDrinkFragment)
    }

    override fun onShowEditNewDrinkFragment(bundle: Bundle) {
        navController.navigate(R.id.addNewDrinkFragment, bundle)
    }
}