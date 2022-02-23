package com.utmaximur.data.update


import android.app.Activity
import android.content.IntentSender
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.tasks.Task
import javax.inject.Inject


class UpdateManager @Inject constructor() {

    interface UpdateListener {
        fun onShowUpdateDialog()
    }

    private var appUpdateManager: AppUpdateManager? = null
    private var updateListener: UpdateListener? = null

    fun update(activity: Activity, onShowDialog: () -> Unit) {
        attachUpdateListener(object : UpdateListener {
            override fun onShowUpdateDialog() {
                onShowDialog()
            }
        })
        checkForUpdate(activity)
        registerListener()
    }

    fun completeUpdate() {
        detachUpdateListener()
        if (appUpdateManager == null) return
        appUpdateManager?.completeUpdate()
    }

    private fun checkForUpdate(activity: Activity) {
        appUpdateManager = AppUpdateManagerFactory.create(activity)
        val appUpdateInfoTask: Task<AppUpdateInfo> =
            appUpdateManager?.appUpdateInfo as Task<AppUpdateInfo>
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                if (updateListener != null) {
                    updateListener?.onShowUpdateDialog()
                }
            } else if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
            ) {
                if (!activity.isFinishing) {
                    try {
                        startUpdate(appUpdateInfo, activity)
                    } catch (e: IntentSender.SendIntentException) {
                    }
                }
            }
        }
    }

    private fun registerListener() {
        if (appUpdateManager == null) return
        appUpdateManager?.registerListener(listener)
    }

    @Throws(IntentSender.SendIntentException::class)
    private fun startUpdate(appUpdateInfo: AppUpdateInfo, activity: Activity) {
        if (appUpdateManager == null) return
        val options = AppUpdateOptions.newBuilder(AppUpdateType.FLEXIBLE).build()
        appUpdateManager?.startUpdateFlow(
            appUpdateInfo,
            activity,
            options
        )
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

    private fun attachUpdateListener(listener: UpdateListener?) {
        updateListener = listener
    }

    private fun detachUpdateListener() {
        updateListener = null
    }
}