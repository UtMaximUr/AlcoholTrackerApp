package com.utmaximur.actions.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.coroutineExecutorFactory
import com.utmaximur.domain.actions.PlatformFileProviderData
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory


@Factory
internal class ActionsImageStoreFactory(
    storeFactory: StoreFactory,
    providerData: PlatformFileProviderData
) : ActionsImageStore,
    Store<ActionsImageStore.Intent, ActionsImageStore.State, ActionsImageStore.Label> by storeFactory.create(
        name = ActionsImageStore::class.simpleName,
        initialState = ActionsImageStore.State,
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = coroutineExecutorFactory {
            onIntent<ActionsImageStore.Intent> { intent ->
                when (intent) {
                    is ActionsImageStore.Intent.SelectedFile -> launch {
                        providerData.sendData(intent.platformFile.uriString)
                        publish(ActionsImageStore.Label.CloseEvent)
                    }

                    is ActionsImageStore.Intent.SelectedFiles -> launch {
                        intent.platformFiles.ifEmpty {
                            publish(ActionsImageStore.Label.CloseEvent)
                        }
                        providerData.sendData(intent.platformFiles.first().uriString)
                        publish(ActionsImageStore.Label.CloseEvent)
                    }

                    ActionsImageStore.Intent.DeleteFile -> launch {
                        providerData.sendData(String())
                        publish(ActionsImageStore.Label.DeleteFile)
                    }
                }
            }
        }
    )