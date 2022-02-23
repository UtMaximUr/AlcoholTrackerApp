package com.utmaximur.data.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.utmaximur.data.assets.AssetsModule
import com.utmaximur.data.local_data_source.AlcoholTrackDatabase
import com.utmaximur.data.local_data_source.AppDatabaseMigrations
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
                    AppDatabaseMigrations.migration1to2(alcoholTrackDatabase),
                    AppDatabaseMigrations.migration2to3(context, alcoholTrackDatabase),
                    AppDatabaseMigrations.migration3to4(),
                    AppDatabaseMigrations.migration4to5(alcoholTrackDatabase)
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