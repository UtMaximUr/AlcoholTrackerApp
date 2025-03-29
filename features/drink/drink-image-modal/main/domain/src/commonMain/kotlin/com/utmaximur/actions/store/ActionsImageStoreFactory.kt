package com.utmaximur.actions.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.utmaximur.app.base.app.ApplicationInfo
import com.utmaximur.domain.actions.PathFileProviderData
import org.koin.core.annotation.Factory

@Factory
internal class ActionsImageStoreFactory(
    storeFactory: StoreFactory,
    providerData: PathFileProviderData,
    applicationInfo: ApplicationInfo
) : ActionsImageStore,
    Store<ActionsImageStore.Intent, ActionsImageStore.State, ActionsImageStore.Label> by storeFactory.create(
        name = ActionsImageStore::class.simpleName,
        initialState = ActionsImageStore.State(),
        bootstrapper = SimpleBootstrapper(Unit),
        executorFactory = {
            ActionsImageExecutor(
                providerData = providerData,
                applicationInfo = applicationInfo
            )
        },
        reducer = { message ->
            when (message) {
                is Message.UpdateGenerateImageState ->
                    copy(isImageGenerationAvailable = message.isImageGenerationAvailable)
            }
        }
    )
