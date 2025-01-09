package com.utmaximur.app.base.coroutines

import kotlinx.coroutines.CoroutineScope
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

@Named("CoroutineScopeIO")
annotation class NamedCoroutineScopeIO

@Named("CoroutineScopeMain")
annotation class NamedCoroutineScopeMain

@Named("CoroutineScopeDefault")
annotation class NamedCoroutineScopeDefault

@Single
@NamedCoroutineScopeIO
fun provideCoroutineScopeIO() = CoroutineScope(provideDispatcherIO())

@Single
@NamedCoroutineScopeMain
fun provideCoroutineScopeMain() = CoroutineScope(provideDispatcherMain())

@Single
@NamedCoroutineScopeDefault
fun provideCoroutineScopeDefault() = CoroutineScope(provideDispatcherDefault())