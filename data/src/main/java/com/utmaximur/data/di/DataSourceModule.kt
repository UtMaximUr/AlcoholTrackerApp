package com.utmaximur.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.utmaximur.data.assets.AssetsModule
import com.utmaximur.data.local_data_source.AlcoholTrackDatabase
import com.utmaximur.data.local_data_source.AppDatabaseMigrations
import com.utmaximur.data.utils.DB_NAME
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
            Room.databaseBuilder(context, AlcoholTrackDatabase::class.java, DB_NAME)
                .addMigrations(
                    AppDatabaseMigrations.migration1_2,
                    AppDatabaseMigrations.migration2_3,
                    AppDatabaseMigrations.migration3_4,
                    AppDatabaseMigrations.migration4_5
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
}