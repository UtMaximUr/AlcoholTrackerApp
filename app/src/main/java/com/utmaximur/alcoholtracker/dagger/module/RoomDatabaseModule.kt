package com.utmaximur.alcoholtracker.dagger.module

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.utmaximur.alcoholtracker.R
import com.utmaximur.alcoholtracker.data.AlcoholTrackDatabase
import com.utmaximur.alcoholtracker.data.model.Drink
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Module
class RoomDatabaseModule(private var application: Application) {

    private lateinit var alcoholTrackDatabase: AlcoholTrackDatabase

    private val databaseCallback = object : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            Log.d("RoomDatabaseModule", "onCreate")
            CoroutineScope(Dispatchers.IO).launch {
                initRealmWithData(application).forEach {
                    alcoholTrackDatabase.getDrinkDao().addDrink(it)
                }
            }
        }
    }

    fun initRealmWithData(context: Context): List<Drink>{
        val drinks: ArrayList<Drink> = ArrayList()

        drinks.add(
            Drink(
                UUID.randomUUID().toString(),
                context.getString(R.string.enum_beer),
                getDoubleDegree(3.5, 50),
                R.array.volume_beer_array,
                R.raw.ic_beer,
                R.raw.beer
            )
        )
        drinks.add(
            Drink(
                UUID.randomUUID().toString(),
                context.getString(R.string.enum_absent),
                getIntDegree(69, 15),
                R.array.volume_absent_array,
                R.raw.ic_absent,
                R.raw.absent
            )
        )
        drinks.add(
            Drink(
                UUID.randomUUID().toString(),
                context.getString(R.string.enum_brandy),
                getIntDegree(39, 5),
                R.array.volume_brandy_array,
                R.raw.ic_brandy,
                R.raw.brandy
            )
        )
        drinks.add(
            Drink(
                UUID.randomUUID().toString(),
                context.getString(R.string.enum_champagne),
                getIntDegree(8, 18),
                R.array.volume_champagne_array,
                R.raw.ic_champagne,
                R.raw.champagne
            )
        )
        drinks.add(
            Drink(
                UUID.randomUUID().toString(),
                context.getString(R.string.enum_cocktail),
                getDoubleDegree(5.0, 20),
                R.array.volume_cocktail_array,
                R.raw.ic_cocktail,
                R.raw.coctail
            )
        )
        drinks.add(
            Drink(
                UUID.randomUUID().toString(),
                context.getString(R.string.enum_cognac),
                getIntDegree(39, 5),
                R.array.volume_cognac_array,
                R.raw.ic_cognac,
                R.raw.cognac
            )
        )
        drinks.add(
            Drink(
                UUID.randomUUID().toString(),
                context.getString(R.string.enum_liqueur),
                getIntDegree(14, 30),
                R.array.volume_liquor_array,
                R.raw.ic_liqueur,
                R.raw.liquor
            )
        )
        drinks.add(
            Drink(
                UUID.randomUUID().toString(),
                context.getString(R.string.enum_tequila),
                getIntDegree(34, 20),
                R.array.volume_tequila_array,
                R.raw.ic_tequila,
                R.raw.tequila
            )
        )
        drinks.add(
            Drink(
                UUID.randomUUID().toString(),
                context.getString(R.string.enum_vodka),
                getIntDegree(37, 18),
                R.array.volume_vodka_array,
                R.raw.ic_vodka,
                R.raw.vodka
            )
        )
        drinks.add(
            Drink(
                UUID.randomUUID().toString(),
                context.getString(R.string.enum_whiskey),
                getIntDegree(39, 10),
                R.array.volume_whiskey_array,
                R.raw.ic_whiskey,
                R.raw.whiskey
            )
        )
        drinks.add(
            Drink(
                UUID.randomUUID().toString(),
                context.getString(R.string.enum_gin),
                getIntDegree(37, 10),
                R.array.volume_gin_array,
                R.raw.ic_gin,
                R.raw.gin
            )
        )
        drinks.add(
            Drink(
                UUID.randomUUID().toString(),
                context.getString(R.string.enum_wine),
                getIntDegree(9, 14),
                R.array.volume_wine_array,
                R.raw.ic_wine,
                R.raw.wine
            )
        )
        drinks.add(
            Drink(
                UUID.randomUUID().toString(),
                context.getString(R.string.enum_cider),
                getDoubleDegree(6.0, 20),
                R.array.volume_cider_array,
                R.raw.ic_cider,
                R.raw.cider
            )
        )
        drinks.add(
            Drink(
                UUID.randomUUID().toString(),
                context.getString(R.string.enum_shot),
                getIntDegree(35, 40),
                R.array.volume_shots_array,
                R.raw.ic_shot,
                R.raw.shots
            )
        )
        drinks.add(
            Drink(
                UUID.randomUUID().toString(),
                context.getString(R.string.enum_rum),
                getIntDegree(37, 12),
                R.array.volume_rum_array,
                R.raw.ic_rum,
                R.raw.rum
            )
        )
        return drinks
    }

    private fun getDoubleDegree(degree: Double, size: Int): List<String?> {
        val nums: Array<String?> = arrayOfNulls(size)
        var double = degree
        for (i in 0 until size) {
            double += 0.5
            val format: String = String.format("%.1f", double)
            nums[i] = format
        }
        return nums.toList()
    }

    private fun getIntDegree(degree: Int, size: Int): List<String?> {
        val nums: Array<String?> = arrayOfNulls(size)
        var n = degree
        for (i in 0 until size) {
            n += 1
            nums[i] = n.toString()
        }
        return nums.toList()
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
}