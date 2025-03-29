package com.utmaximur.workmanager.initializer

import android.content.Context
import androidx.startup.Initializer
import androidx.work.Configuration
import androidx.work.WorkManager
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import com.utmaximur.workmanager.SimpleWorkManager
import com.utmaximur.workmanager.db.getDatabaseBuilder
import com.utmaximur.workmanager.dependacy.ApplicationContainer
import com.utmaximur.workmanager.dependacy.CommonWorkManagerOperationFactory
import com.utmaximur.workmanager.dependacy.DatabaseFactory
import com.utmaximur.workmanager.work.RealWorker

@Suppress("unused") //used in AndroidManifest.xml
internal class SimpleWorkerInitializer : Initializer<Unit> {
    @OptIn(DelicateCoroutinesApi::class)
    override fun create(context: Context) {
        println("created SimpleWorkerInitializer")
        val database = DatabaseFactory(databaseBuilder = getDatabaseBuilder(context)).create()

        if (!WorkManager.isInitialized()) {
            WorkManager.initialize(
                context,
                Configuration.Builder()
                    .setWorkerFactory(RealWorker.RealWorkerFactory(dao = database.workerDao()))
                    .build()
            )
        }

        val dependenciesContainer = ApplicationContainer(
            database = database,
            commonWorkManagerOperationFactory = CommonWorkManagerOperationFactory(
                WorkManager.getInstance(context),
                dao = database.workerDao(),
                applicationScope = GlobalScope
            )
        )

        SimpleWorkManager.applicationContainer = dependenciesContainer
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }

}