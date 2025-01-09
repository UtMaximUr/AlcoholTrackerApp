package com.utmaximur.settings.store

import com.arkivanov.mvikotlin.core.store.Store
import com.utmaximur.domain.EMPTY_STRING
import com.utmaximur.settings.BuildKonfig
import com.utmaximur.settings.store.SettingsStore.Intent
import com.utmaximur.settings.store.SettingsStore.State

interface SettingsStore : Store<Intent, State, Nothing> {

    data class State(
        val appVersion: String,
        val isDarkTheme: Boolean,
        val currency: String,
        val privacyPolicyUrl: String,
        val termsOrUseUrl: String
    ) {
        constructor() : this(
            appVersion = EMPTY_STRING,
            isDarkTheme = false,
            currency = EMPTY_STRING,
            privacyPolicyUrl = BuildKonfig.URL_PRIVACY,
            termsOrUseUrl = BuildKonfig.URL_TERMS
        )
    }

    sealed interface Intent {

        data class ActiveDarkTheme(val active: Boolean) : Intent

        data class SelectedCurrency(val currency: String) : Intent
    }
}