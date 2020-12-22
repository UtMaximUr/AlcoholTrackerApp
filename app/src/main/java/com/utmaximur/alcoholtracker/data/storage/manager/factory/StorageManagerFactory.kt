package com.utmaximur.alcoholtracker.data.storage.manager.factory

import android.content.Context
import com.utmaximur.alcoholtracker.data.storage.manager.impl.StorageManagerImpl
import com.utmaximur.alcoholtracker.data.storage.module.StorageModule
import com.utmaximur.alcoholtracker.data.storage.module.impl.StorageModuleImpl
import com.utmaximur.alcoholtracker.data.storage.service.StorageService


class StorageManagerFactory {
    companion object {
        fun createManager(context: Context): StorageManagerImpl {
            val storageService: StorageService = StorageService.getAppDataBase(context)!!
            val storageModule: StorageModule = StorageModuleImpl(storageService)
            return StorageManagerImpl(storageModule)
        }
    }
}