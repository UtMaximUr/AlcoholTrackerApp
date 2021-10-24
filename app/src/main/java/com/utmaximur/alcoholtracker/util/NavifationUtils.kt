package com.utmaximur.alcoholtracker.util

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

fun <T> Fragment.getNavigationResult(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.get<T>(key)

fun <T> Fragment.getNavigationResultLiveData(key: String = "result") =
    findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<T>(key)

fun <T> Fragment.setNavigationResult(key: String = "result", result: T, isRemove: Boolean) {
    findNavController().previousBackStackEntry?.savedStateHandle?.set(key, result)
    if (isRemove) {
        findNavController().previousBackStackEntry?.savedStateHandle?.remove<T>(key)
    }
}

fun <T> Fragment.removeNavigationResult(key: String = "result") {
    findNavController().previousBackStackEntry?.savedStateHandle?.remove<T>(key)
}