package com.utmaximur.alcoholtracker.dagger.module

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.utmaximur.alcoholtracker.data.AlcoholTrackDatabase
import com.utmaximur.alcoholtracker.data.resources.IconRaw
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.file.FileGenerator
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Singleton

@Module
class RoomDatabaseModule(private var application: Application) {

    private lateinit var alcoholTrackDatabase: AlcoholTrackDatabase

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

    @Singleton
    @Provides
    fun providesRoomDatabase(): AlcoholTrackDatabase {
        alcoholTrackDatabase = Room.databaseBuilder(application, AlcoholTrackDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .addCallback(databaseCallback)
            .build()
        return alcoholTrackDatabase
    }

    @Singleton
    @Provides
    fun providesTrackDAO(alcoholTrackDatabase: AlcoholTrackDatabase) = alcoholTrackDatabase.getTrackDao()

    @Singleton
    @Provides
    fun providesDrinkDAO(alcoholTrackDatabase: AlcoholTrackDatabase) = alcoholTrackDatabase.getDrinkDao()

    @Provides
    fun provideIcons(): IconRaw {
        return IconRaw()
    }

    @Provides
    fun provideFile(): FileGenerator {
        return FileGenerator()
    }
}