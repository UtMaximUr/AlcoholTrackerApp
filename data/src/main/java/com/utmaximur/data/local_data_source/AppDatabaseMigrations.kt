package com.utmaximur.data.local_data_source

import android.content.Context
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.utmaximur.data.assets.AssetsModule
import com.utmaximur.data.utils.addImageField
import com.utmaximur.data.utils.convertMigrationModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppDatabaseMigrations {


    companion object {
        val migration1_2 = object : Migration(1, 2) {
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

            }
        }

        val migration2_3 = object : Migration(2, 3) {
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
            }
        }

        val migration3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE track_database ADD COLUMN event TEXT NOT NULL DEFAULT ''"
                )
            }
        }

        val migration4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "ALTER TABLE track_database ADD COLUMN image TEXT NOT NULL DEFAULT ''"
                )
            }
        }


        private fun updateDrinkDb(context: Context, alcoholTrackDatabase: AlcoholTrackDatabase) {
            CoroutineScope(Dispatchers.IO).launch {
                AssetsModule(context.assets).getDrinkList().forEach {
                    alcoholTrackDatabase.getDrinkDao().addDrink(it)
                }
            }
        }

        private fun updateTrackDb(alcoholTrackDatabase: AlcoholTrackDatabase) {
            val coroutineScope = CoroutineScope(Dispatchers.IO).launch {
                alcoholTrackDatabase.getTrackDao().getTracks().forEach { alcoholTrack ->
                    alcoholTrackDatabase.getTrackDao().updateTrack(
                        alcoholTrack.convertMigrationModel()
                    )
                }
            }
            coroutineScope.cancel()
        }

        private fun updateTrackDbAddImageField(alcoholTrackDatabase: AlcoholTrackDatabase) {
            CoroutineScope(Dispatchers.IO).launch {
                alcoholTrackDatabase.getTrackDao().getTracks().forEach { alcoholTrack ->
                    alcoholTrackDatabase.getTrackDao().updateTrack(
                        alcoholTrack.addImageField()
                    )
                }
            }
        }
    }
}