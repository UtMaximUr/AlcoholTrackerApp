package com.utmaximur.alcoholtracker.data.backup

import android.content.Context


import java.io.*
import java.lang.Exception
import android.content.ContentResolver
import android.net.Uri

import java.nio.channels.FileChannel


class BackupModule(private val context: Context) {

    private val dbName = "app_database-wal"

    fun backupDatabase() {
//        val appDatabase: AlcoholTrackDatabase = RoomDatabaseModule().providesRoomDatabase()
//        appDatabase.close()

        val dbFile: File = context.getDatabasePath(dbName)
        val sDir = File(context.filesDir, "backup")
        val fileName = "FILE_NAME_1"
        val sfPath: String = sDir.path + File.separator.toString() + fileName
        if (!sDir.exists()) {
            sDir.mkdirs()
        } else {
            checkAndDeleteBackupFile(sDir, sfPath)
        }
        val saveFile = File(sfPath)
        if (saveFile.exists()) {
            saveFile.delete()
        }
        try {
            if (saveFile.createNewFile()) {
                val bufferSize = 8 * 1024
                val buffer = ByteArray(bufferSize)
                var bytesRead: Int
                val saveDb: OutputStream = FileOutputStream(sfPath)
                val inDb: InputStream = FileInputStream(dbFile)
                while (inDb.read(buffer, 0, bufferSize).also { bytesRead = it } > 0) {
                    saveDb.write(buffer, 0, bytesRead)
                }
                saveDb.flush()
                inDb.close()
                saveDb.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun checkAndDeleteBackupFile(directory: File, path: String) {
        val currentDateFile = File(path)
        var fileIndex = -1
        var lastModifiedTime = System.currentTimeMillis()
        if (!currentDateFile.exists()) {
            val files = directory.listFiles()
            if (files != null && files.size >= 10_000) {
                for (i in files.indices) {
                    val file = files[i]
                    val fileLastModifiedTime = file.lastModified()
                    if (fileLastModifiedTime < lastModifiedTime) {
                        lastModifiedTime = fileLastModifiedTime
                        fileIndex = i
                    }
                }
                if (fileIndex != -1) {
                    val deletingFile = files[fileIndex]
                    if (deletingFile.exists()) {
                        deletingFile.delete()
                    }
                }
            }
        }
    }

    fun validFile(fileUri: Uri): Boolean {
        val cr: ContentResolver = context.contentResolver
        val mime = cr.getType(fileUri)
        return "application/octet-stream" == mime
    }

    fun restoreDatabase(uri: Uri?) {
//        val appDatabase: AppDatabase = AppDatabase.getAppDatabase(context)
//        appDatabase.close()

        val sDir = File(context.filesDir, "backup")
        val fileName = "FILE_NAME_1"
        val sfPath: String = "file://" + sDir.path + File.separator.toString() + fileName
        val inputStream: InputStream? = context.contentResolver.openInputStream(Uri.parse(sfPath))


//        deleteRestoreBackupFile()
        backupDatabaseForRestore()
        val oldDB: File = context.getDatabasePath(dbName)
        if (inputStream != null) {
            try {
                copyFile(inputStream as FileInputStream, FileOutputStream(oldDB))
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }


    private fun copyFile(fromFile: FileInputStream, toFile: FileOutputStream) {
        var fromChannel: FileChannel? = null
        var toChannel: FileChannel? = null
        try {
            fromChannel = fromFile.channel
            toChannel = toFile.channel
            fromChannel.transferTo(0, fromChannel.size(), toChannel)
        } finally {
            try {
                fromChannel?.close()
            } finally {
                toChannel?.close()
            }
        }
    }


//    private fun deleteRestoreBackupFile() {
//        val directory = File(context.filesDir, "backup")
//        val sfPath: String = directory.path + File.separator + "BACKUP_RESTORE_ROLLBACK_FILE_NAME"
//        val restoreFile = File(sfPath)
//        if (restoreFile.exists()) {
//            restoreFile.delete()
//        }
//    }

    private fun backupDatabaseForRestore() {
        val dbFile = context.getDatabasePath(dbName)
        val sDir = File(context.filesDir, "backup")
        val sfPath: String = sDir.path + File.separator + "BACKUP_RESTORE_ROLLBACK_FILE_NAME"
        if (!sDir.exists()) {
            sDir.mkdirs()
        }
        val saveFile = File(sfPath)
        if (saveFile.exists()) {
            saveFile.delete()
        }
        try {
            if (saveFile.createNewFile()) {
                val bufferSize = 8 * 1024
                val buffer = ByteArray(bufferSize)
                var bytesRead: Int
                val saveDb: OutputStream = FileOutputStream(sfPath)
                val inDb: InputStream = FileInputStream(dbFile)
                while (inDb.read(buffer, 0, bufferSize).also { bytesRead = it } > 0) {
                    saveDb.write(buffer, 0, bytesRead)
                }
                saveDb.flush()
                inDb.close()
                saveDb.close()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}