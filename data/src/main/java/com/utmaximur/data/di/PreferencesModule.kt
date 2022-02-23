package com.utmaximur.data.di

import android.content.Context
import android.content.SharedPreferences
import com.utmaximur.data.preferences.SharedPref
import com.utmaximur.data.utils.PREFS_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class PreferencesModule {

    @Provides
    fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences =
        context.getSharedPreferences(
            PREFS_NAME, Context.MODE_PRIVATE
        )

    @Singleton
    @Provides
    fun providesSharedPref(sharedPreferences: SharedPreferences) = SharedPref(sharedPreferences)
}