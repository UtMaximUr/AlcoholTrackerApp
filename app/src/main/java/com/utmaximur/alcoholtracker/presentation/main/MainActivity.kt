package com.utmaximur.alcoholtracker.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import com.utmaximur.alcoholtracker.data.update.UpdateManager
import com.utmaximur.alcoholtracker.data.update.UpdateManager.Companion.getInstance
import com.utmaximur.alcoholtracker.presentation.main.ui.MainScreen
import com.utmaximur.alcoholtracker.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), UpdateManager.UpdateListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }

    override fun onShowUpdateDialog() {
//        navController.navigate(R.id.updateBottomDialogFragment)
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