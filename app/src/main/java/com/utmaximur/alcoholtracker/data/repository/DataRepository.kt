package com.utmaximur.alcoholtracker.data.repository

import android.net.Uri
import com.utmaximur.alcoholtracker.data.backup.BackupModule

class DataRepository(private val backupModule: BackupModule) {

    fun backupDatabase() = backupModule.backupDatabase()

    fun validFile(uri: Uri) = backupModule.validFile(uri)

    fun restoreDatabase(uri: Uri?) = backupModule.restoreDatabase(uri)
}