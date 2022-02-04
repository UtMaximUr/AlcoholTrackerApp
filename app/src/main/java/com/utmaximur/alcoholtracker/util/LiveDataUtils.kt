package com.utmaximur.alcoholtracker.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T : Any> LiveData<T>.setValue(value: T) {
    (this as MutableLiveData).value = value
}

fun <T : Any> LiveData<T>.setPostValue(value: T) {
    (this as MutableLiveData).postValue(value)
}