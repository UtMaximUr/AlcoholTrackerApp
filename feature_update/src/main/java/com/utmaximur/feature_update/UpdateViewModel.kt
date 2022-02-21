package com.utmaximur.feature_update

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.utmaximur.domain.interactor.UpdateInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UpdateViewModel @Inject constructor(private val updateInteractor: UpdateInteractor): ViewModel() {

    val visibleState: LiveData<Boolean> by lazy {
        MutableLiveData()
    }

    fun init(activity: Activity) {
        if (updateInteractor.isSavedUpdate()) {
            updateInteractor.update(activity) {
                (visibleState as MutableLiveData).value = true
            }
        }
    }

    fun onNowClick() {
        updateInteractor.completeUpdate()
    }

    fun onLaterClick() {
        (visibleState as MutableLiveData).value = false
    }
}