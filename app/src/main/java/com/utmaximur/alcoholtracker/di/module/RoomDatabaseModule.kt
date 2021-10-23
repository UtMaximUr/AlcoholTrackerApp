@file:Suppress("PrivatePropertyName")

package com.utmaximur.alcoholtracker.di.module

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.utmaximur.alcoholtracker.data.AlcoholTrackDatabase
import com.utmaximur.alcoholtracker.data.assets.AssetsModule
import com.utmaximur.alcoholtracker.data.file.FileManager
import com.utmaximur.alcoholtracker.util.addImageField
import com.utmaximur.alcoholtracker.util.convertMigrationModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton


@Module
class RoomDatabaseModule(private var application: Application) {

    private lateinit var alcoholTrackDatabase: AlcoholTrackDatabase

    @Singleton
    @Provides
    fun providesRoomDatabase(): AlcoholTrackDatabase {
        alcoholTrackDatabase =
            Room.databaseBuilder(application, AlcoholTrackDatabase::class.java, "app_database")
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
                .addCallback(databaseCallback)
                .build()
        return alcoholTrackDatabase
    }

    @Singleton
    @Provides
    fun providesTrackDAO(alcoholTrackDatabase: AlcoholTrackDatabase) =
        alcoholTrackDatabase.getTrackDao()

    @Singleton
    @Provides
    fun providesDrinkDAO(alcoholTrackDatabase: AlcoholTrackDatabase) =
        alcoholTrackDatabase.getDrinkDao()

    @Provides
    fun provideAssets(): AssetsModule {
        return AssetsModule(application.assets)
    }

    @Provides
    fun provideFile(): FileManager {
        return FileManager(application.applicationContext)
    }

    /**
     * init database
     */

    private val databaseCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                provideAssets().getDrinkList().forEach {
                    alcoholTrackDatabase.getDrinkDao().addDrink(it)
                }
            }
        }
    }

    /**
     * migration database
     */

    private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {

            database.execSQL(
                "CREATE TABLE track_database_new (id TEXT NOT NULL," +
                        " drink TEXT NOT NULL," +
                        " volume TEXT NOT NULL," +
                        " quantity INTEGER NOT NULL," +
                        " degree TEXT NOT NULL," +
                        " price REAL NOT NULL," +
                        " date INTEGER NOT NULL," +
                        " icon TEXT NOT NULL," +
                        " PRIMARY KEY(id))"
            )
            database.execSQL(
                "INSERT INTO track_database_new (id, drink, volume, quantity, degree, price, date, icon) " +
                        "SELECT id, drink, volume, quantity, degree, price, date, icon FROM track_database"
            )
            database.execSQL("DROP TABLE track_database")
            database.execSQL("ALTER TABLE track_database_new RENAME TO track_database")

            updateTrackDb()
        }
    }

    private val MIGRATION_2_3: Migration = object : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {

            database.execSQL(
                "CREATE TABLE drink_database_new (id TEXT NOT NULL," +
                        " drink TEXT NOT NULL," +
                        " degree TEXT NOT NULL," +
                        " volume TEXT NOT NULL," +
                        " icon TEXT NOT NULL," +
                        " photo TEXT NOT NULL," +
                        " PRIMARY KEY(id))"
            )

            database.execSQL("DROP TABLE drink_database")
            database.execSQL("ALTER TABLE drink_database_new RENAME TO drink_database")

            updateDrinkDb()
        }
    }

    private val MIGRATION_3_4: Migration = object : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE track_database ADD COLUMN event TEXT NOT NULL DEFAULT ''"
            )
        }
    }

    private val MIGRATION_4_5: Migration = object : Migration(4, 5) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL(
                "ALTER TABLE track_database ADD COLUMN image TEXT NOT NULL DEFAULT ''"
            )
            updateTrackDbAddImageField()
        }
    }

    private fun updateDrinkDb() {
        CoroutineScope(Dispatchers.IO).launch {
            provideAssets().getDrinkList().forEach {
                alcoholTrackDatabase.getDrinkDao().addDrink(it)
            }
        }
    }

    private fun updateTrackDb() {
        val coroutineScope = CoroutineScope(Dispatchers.IO).launch {
            alcoholTrackDatabase.getTrackDao().singleRequestTracks().forEach { alcoholTrack ->
                alcoholTrackDatabase.getTrackDao().updateTrack(
                    alcoholTrack.convertMigrationModel(application)
                )
            }
        }
        coroutineScope.cancel()
    }

    private fun updateTrackDbAddImageField() {
        CoroutineScope(Dispatchers.IO).launch {
            alcoholTrackDatabase.getTrackDao().singleRequestTracks().forEach { alcoholTrack ->
                alcoholTrackDatabase.getTrackDao().updateTrack(
                    alcoholTrack.addImageField(application)
                )
            }
        }
    }
}