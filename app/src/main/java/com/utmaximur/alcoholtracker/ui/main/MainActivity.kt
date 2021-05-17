package com.utmaximur.alcoholtracker.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.update.UpdateManager
import com.utmaximur.alcoholtracker.data.update.UpdateManager.Companion.getInstance
import com.utmaximur.alcoholtracker.ui.add.AddFragment
import com.utmaximur.alcoholtracker.ui.addmydrink.AddNewDrink
import com.utmaximur.alcoholtracker.ui.calendar.CalendarFragment
import com.utmaximur.alcoholtracker.util.*


class MainActivity : AppCompatActivity(),
    AddFragment.AddFragmentListener,
    AddNewDrink.AddNewFragmentListener,
    CalendarFragment.CalendarFragmentListener,
    UpdateManager.UpdateListener {

    private lateinit var menu: BottomNavigationView
    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun findViewById() {
        menu = findViewById(R.id.bottom_navigation_view)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
    }

    private fun initView() {
        findViewById()
        navController = navHostFragment.navController
        menu.setupWithNavController(navController)
    }

    override fun showEditAlcoholTrackerFragment(bundle: Bundle) {
        navController.navigate(R.id.addFragment, bundle)
    }

    override fun onShowAddNewDrinkFragment() {
        navController.navigate(R.id.addNewDrinkFragment)
    }

    override fun onShowEditNewDrinkFragment(bundle: Bundle) {
        navController.navigate(R.id.addNewDrinkFragment, bundle)
    }

    override fun onShowUpdateDialog() {
        navController.navigate(R.id.updateBottomDialogFragment)
    }

    override fun showAddAlcoholTrackerFragment(bundle: Bundle?) {
        navController.navigate(R.id.addFragment, bundle)
    }

    override fun closeFragment() {
        navController.popBackStack()
    }

    override fun onHideNavigationBar() {
        menu.toGone()
    }

    override fun onShowNavigationBar() {
        menu.toVisible()
    }

    override fun onStart() {
        super.onStart()
        if (getSavedUpdate()!!) {
            getInstance().registerListener()
            getInstance().attachUpdateListener(this)
            getInstance().checkForUpdate(this)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        if (requestCode == UPDATE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                onShowUpdateDialog()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private val sharedPrefs by lazy { getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE) }

    private fun getSavedUpdate() = sharedPrefs?.getBoolean(KEY_UPDATE, UPDATE_UNDEFINED)
}