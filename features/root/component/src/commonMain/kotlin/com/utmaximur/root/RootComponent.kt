package com.utmaximur.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.utmaximur.core.decompose.ComposeComponent
import com.utmaximur.domain.root.store.RootStore
import com.utmaximur.message.MessageComponent
import kotlinx.coroutines.flow.StateFlow

interface RootComponent {

    val model: StateFlow<RootStore.State>
    val stack: Value<ChildStack<*, ComposeComponent>>
    val messageComponent: MessageComponent

    fun onCalendarScreenClicked()

    fun onMapScreenClicked()

    fun onAlcoholCalculatorScreenClicked()

    fun onStatisticScreenClicked()

    fun onSettingsScreenClicked()

    fun handleDeepLink(deepLink: String?)
}