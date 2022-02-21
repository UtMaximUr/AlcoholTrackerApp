package com.utmaximur.domain.repository

import android.app.Activity

interface UpdateRepository {
    fun update(activity: Activity, onShowDialog: () -> Unit)
    fun completeUpdate()
}