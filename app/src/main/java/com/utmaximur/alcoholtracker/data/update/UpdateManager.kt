package com.utmaximur.alcoholtracker.data.update

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.tasks.Task
import timber.log.Timber


class UpdateManager {

    interface UpdateListener {
        fun onShowUpdateDialog()
    }

    private var appUpdateManager: AppUpdateManager? = null
    private var updateListener: UpdateListener? = null

    fun attachUpdateListener(listener: UpdateListener?) {
        updateListener = listener
    }

    fun detachUpdateListener() {
        updateListener = null
    }

    fun checkForUpdate(context: Context) {
        appUpdateManager = AppUpdateManagerFactory.create(context)
        val appUpdateInfoTask: Task<AppUpdateInfo> =
            appUpdateManager?.appUpdateInfo as Task<AppUpdateInfo>
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            Timber.tag("debug_log")
            Timber.d("appUpdateInfo = ${appUpdateInfo.installStatus()}")
            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                if (updateListener != null) {
                    updateListener?.onShowUpdateDialog()
                }
            }
        }
    }

    fun registerListener() {
        if (appUpdateManager == null) return
        appUpdateManager?.registerListener(listener)
    }

    fun completeUpdate() {
        if (appUpdateManager == null) return
        appUpdateManager?.completeUpdate()
    }

    private val listener =
        InstallStateUpdatedListener { state: InstallState ->
            if (state.installStatus() == InstallStatus.DOWNLOADED) {
                onUpdateDownloaded()
            }
        }

    private fun onUpdateDownloaded() {
        if (appUpdateManager == null) return
        appUpdateManager?.unregisterListener(listener)
        if (updateListener != null) {
            updateListener?.onShowUpdateDialog()
        } else {
            appUpdateManager?.completeUpdate()
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: UpdateManager? = null

        @JvmStatic
        fun getInstance(): UpdateManager =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: UpdateManager()
                        .also {
                            INSTANCE = it
                        }
            }
    }
}