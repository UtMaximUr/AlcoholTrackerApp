package com.utmaximur.app.base.coroutines

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.IO
import kotlinx.coroutines.newSingleThreadContext
import org.koin.core.annotation.Named
import org.koin.core.annotation.Single

/*
Не работают два annotation class в одном конструкторе
 */
//TODO это не работает все как ожидалось
@Named("CoroutineDispatcherIO")
annotation class NamedCoroutineDispatcherIO

@Named("CoroutineDispatcherMain")
annotation class NamedCoroutineDispatcherMain

@Named("CoroutineDispatcherDefault")
annotation class NamedCoroutineDispatcherDefault

@Repeatable
@Named("CoroutineDispatcherDatabaseWrite")
annotation class NamedCoroutineDispatcherDatabaseWrite

@Repeatable
@Named("CoroutineDispatcherDatabaseRead")
annotation class NamedCoroutineDispatcherDatabaseRead

const val NAMED_DATABASE_WRITER_DISPATCHER = "CoroutineDispatcherDatabaseWrite"

const val NAMED_DATABASE_READ_DISPATCHER = "CoroutineDispatcherDatabaseRead"

@Single
@NamedCoroutineDispatcherIO
fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

@Single
@NamedCoroutineDispatcherMain
fun provideDispatcherMain(): CoroutineDispatcher = Dispatchers.Main

@Single
@NamedCoroutineDispatcherDefault
fun provideDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default

@OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
@Single
@Named(NAMED_DATABASE_WRITER_DISPATCHER)
fun provideDispatcherDatabaseWrite(): CoroutineDispatcher = newSingleThreadContext("Database writer")

@OptIn(ExperimentalCoroutinesApi::class)
@Single
@Named(NAMED_DATABASE_READ_DISPATCHER)
fun provideDispatcherDatabaseRead(): CoroutineDispatcher = Dispatchers.IO.limitedParallelism(4)