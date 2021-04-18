@file:Suppress("PrivatePropertyName")

package com.utmaximur.alcoholtracker.dagger.module

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import android.os.Handler
import android.os.Looper
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.utmaximur.alcoholtracker.data.AlcoholTrackDatabase
import com.utmaximur.alcoholtracker.data.file.FileGenerator
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.resources.IconRaw
import com.utmaximur.alcoholtracker.util.convertMigrationModel
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Singleton


@Module
class RoomDatabaseModule(private var application: Application) {

    private lateinit var alcoholTrackDatabase: AlcoholTrackDatabase

    @Singleton
    @Provides
    fun providesRoomDatabase(): AlcoholTrackDatabase {
        alcoholTrackDatabase =
            Room.databaseBuilder(application, AlcoholTrackDatabase::class.java, "app_database")
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4)
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
    fun provideIcons(): IconRaw {
        return IconRaw()
    }

    @Provides
    fun provideFile(): FileGenerator {
        return FileGenerator()
    }


    /**
     * init database
     */

    private val databaseCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                getDrinkList(application).forEach {
                    alcoholTrackDatabase.getDrinkDao().addDrink(it)
                }
            }
        }
    }

    fun getDrinkList(context: Context): List<Drink> {
        val json = getJsonFromAssets(context.assets, "drink_list.json")
        val turnsType = object : TypeToken<List<Drink>>() {}.type
        return Gson().fromJson(json, turnsType)
    }

    private fun getJsonFromAssets(assetManager: AssetManager, fileName: String): String {
        var jsonString = ""
        try {
            val inputStream = assetManager.open(fileName)
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            jsonString = String(buffer, Charsets.UTF_8)
        } catch (e: IOException) {
        }
        return jsonString
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

    private fun updateDrinkDb() {
        CoroutineScope(Dispatchers.IO).launch {
            getDrinkList(application).forEach {
                alcoholTrackDatabase.getDrinkDao().addDrink(it)
            }
        }
    }

    private fun updateTrackDb() {
        var coroutineScope: Job?
        val handler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            alcoholTrackDatabase.getTrackDao().getTracks().observeForever { alcoholTrackList ->
                coroutineScope = CoroutineScope(Dispatchers.IO).launch {
                    alcoholTrackList.forEach { alcoholTrack ->
                        alcoholTrackDatabase.getTrackDao().updateTrack(
                            alcoholTrack.convertMigrationModel(application)
                        )
                    }
                }
                coroutineScope?.cancel()
            }
        }
        handler.post(runnable)
    }
}