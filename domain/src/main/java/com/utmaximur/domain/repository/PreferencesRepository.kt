package com.utmaximur.domain.repository

interface PreferencesRepository {
    fun saveUpdateChecked(checked: Boolean)
    fun saveThemeChecked(theme: Int)
    fun isUpdateChecked(): Boolean
    fun getSaveTheme(): Int
}