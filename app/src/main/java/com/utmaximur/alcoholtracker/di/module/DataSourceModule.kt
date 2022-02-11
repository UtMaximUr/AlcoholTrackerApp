package com.utmaximur.alcoholtracker.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.utmaximur.alcoholtracker.data.AlcoholTrackDatabase
import com.utmaximur.alcoholtracker.data.assets.AssetsModule
import com.utmaximur.alcoholtracker.util.addImageField
import com.utmaximur.alcoholtracker.util.convertMigrationModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class DataSourceModule {

    private lateinit var alcoholTrackDatabase: AlcoholTrackDatabase

    @Singleton
    @Provides
    fun providesRoomDatabase(@ApplicationContext context: Context): AlcoholTrackDatabase {
        alcoholTrackDatabase =
            Room.databaseBuilder(context, AlcoholTrackDatabase::class.java, "app_database")
                .addMigrations(
                    migration1to2(context),
                    migration2to3(context),
                    migration3to4(),
                    migration4to5(context)
                )
                .addCallback(databaseCallback(context))
                .build()
        return alcoholTrackDatabase
    }

    private fun databaseCallback(context: Context): RoomDatabase.Callback {
        return object : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                CoroutineScope(Dispatchers.IO).launch {
                    AssetsModule(context.assets).getDrinkList().forEach {
                        alcoholTrackDatabase.getDrinkDao().addDrink(it)
                    }
                }
            }
        }
    }


    /**
     * migration database
     */

    private fun migration1to2(context: Context): Migration {
        return object : Migration(1, 2) {
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

                updateTrackDb(context)
            }
        }
    }

    private fun migration2to3(context: Context): Migration {
        return object : Migration(2, 3) {
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

                updateDrinkDb(context)
            }
        }
    }

    private fun migration3to4(): Migration {
        return object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE track_database ADD COLUMN event TEXT NOT NULL DEFAULT ''"
                )
            }
        }
    }

    private fun migration4to5(context: Context): Migration {
        return object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE track_database ADD COLUMN image TEXT NOT NULL DEFAULT ''"
                )
                updateTrackDbAddImageField(context)
            }
        }
    }

    private fun updateDrinkDb(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            AssetsModule(context.assets).getDrinkList().forEach {
                alcoholTrackDatabase.getDrinkDao().addDrink(it)
            }
        }
    }

    private fun updateTrackDb(context: Context) {
        val coroutineScope = CoroutineScope(Dispatchers.IO).launch {
            alcoholTrackDatabase.getTrackDao().getTracks().forEach { alcoholTrack ->
                alcoholTrackDatabase.getTrackDao().updateTrack(
                    alcoholTrack.convertMigrationModel(context)
                )
            }
        }
        coroutineScope.cancel()
    }

    private fun updateTrackDbAddImageField(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            alcoholTrackDatabase.getTrackDao().getTracks().forEach { alcoholTrack ->
                alcoholTrackDatabase.getTrackDao().updateTrack(
                    alcoholTrack.addImageField(context)
                )
            }
        }
    }
}