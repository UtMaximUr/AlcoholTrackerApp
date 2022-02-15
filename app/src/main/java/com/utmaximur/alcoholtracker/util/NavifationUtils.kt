package com.utmaximur.alcoholtracker.util

import androidx.navigation.NavHostController

fun <T> NavHostController.getNavigationResult(key: String = "result") =
    this.previousBackStackEntry?.savedStateHandle?.get<T>(key)

fun <T> NavHostController.setNavigationResult(key: String = "result", result: T) {
    this.currentBackStackEntry?.savedStateHandle?.set(key, result)
}

fun <T> NavHostController.removeNavigationResult(key: String = "result") {
    this.previousBackStackEntry?.savedStateHandle?.remove<T>(key)
}