package com.utmaximur.alcoholtracker.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.update.UpdateManager
import com.utmaximur.alcoholtracker.data.update.UpdateManager.Companion.getInstance
import com.utmaximur.alcoholtracker.databinding.ActivityMainBinding
import com.utmaximur.alcoholtracker.presentation.create_track.CreateTrackFragment
import com.utmaximur.alcoholtracker.presentation.create_my_drink.CreateMyDrink
import com.utmaximur.alcoholtracker.presentation.settings.DataFragment
import com.utmaximur.alcoholtracker.util.*


class MainActivity : AppCompatActivity(),
    CreateTrackFragment.AddFragmentListener,
    CreateMyDrink.AddNewFragmentListener,
    UpdateManager.UpdateListener,
    DataFragment.DataFragmentListener {

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() = with(binding) {
        this@MainActivity.navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = this@MainActivity.navHostFragment.navController
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.background = null
        addTrack.setOnClickListener { navController.navigate(R.id.addFragment) }
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

    override fun closeFragment() {
        navController.popBackStack()
    }

    override fun onHideNavigationBar() = with(binding) {
        coordinatorLayout.toGone()
    }

    override fun onShowNavigationBar() = with(binding) {
        coordinatorLayout.toVisible()
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