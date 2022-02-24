package com.utmaximur.feature_create_drink.state

sealed class EmptyFieldState {
    data class Empty(
        val isPhotoEmpty: Boolean = false,
        val isNameEmpty: Boolean = false,
        val isIconEmpty: Boolean = false,
        val isDegreeEmpty: Boolean = false,
        val isVolumeEmpty: Boolean = false
    ) : EmptyFieldState()

}