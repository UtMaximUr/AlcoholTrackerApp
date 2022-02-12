package com.utmaximur.alcoholtracker.presentation.create_my_drink.state

sealed class EmptyFieldState {
    data class Empty(
        val isPhotoEmpty: Boolean = true,
        val isNameEmpty: Boolean = true,
        val isIconEmpty: Boolean = true,
        val isDegreeEmpty: Boolean = true,
        val isVolumeEmpty: Boolean = true,
        val isNotEmpty: Boolean = false,
    ) : EmptyFieldState()

}