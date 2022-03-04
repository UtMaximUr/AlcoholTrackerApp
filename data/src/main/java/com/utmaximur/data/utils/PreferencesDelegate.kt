package com.utmaximur.data.utils

import android.content.SharedPreferences
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

private class PreferenceProperty<T>(
    private val sharedPreferences: SharedPreferences,
    private val defaultValue: T,
    private val key: (KProperty<*>) -> String = KProperty<*>::name,
    private val getter: SharedPreferences.(String, T) -> T,
    private val setter: SharedPreferences.Editor.(String, T) -> SharedPreferences.Editor
) : ReadWriteProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>): T =
        sharedPreferences.getter(key(property), defaultValue)

    override fun setValue(thisRef: Any, property: KProperty<*>, value: T) {
        with(sharedPreferences.edit()) {
            setter(key(property), value)
            apply()
        }
    }
}

fun SharedPreferences.int(defaultValue: Int = 0): ReadWriteProperty<Any, Int> =
    PreferenceProperty(
        sharedPreferences = this,
        defaultValue = defaultValue,
        getter = SharedPreferences::getInt,
        setter = SharedPreferences.Editor::putInt
    )

fun SharedPreferences.boolean(
    defaultValue: Boolean = false
): ReadWriteProperty<Any, Boolean> =
    PreferenceProperty(
        sharedPreferences = this,
        defaultValue = defaultValue,
        getter = SharedPreferences::getBoolean,
        setter = SharedPreferences.Editor::putBoolean
    )