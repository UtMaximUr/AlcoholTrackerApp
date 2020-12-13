package com.utmaximur.alcoholtracker

import android.app.Application
import android.content.Context
import com.utmaximur.alcoholtracker.data.storage.manager.StorageManager
import com.utmaximur.alcoholtracker.data.storage.manager.factory.StorageManagerFactory

open class App: Application() {

    private lateinit var storageManager: StorageManager

    override fun onCreate() {
        super.onCreate()
        storageManager = StorageManagerFactory.createManager(this)
    }

    companion object {

        fun getStorageManager(context: Context): StorageManager = getApp(context).storageManager

        private fun getApp(context: Context) = context.applicationContext as App
    }
}