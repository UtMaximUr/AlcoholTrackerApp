package com.utmaximur.alcoholtracker.data.assets

import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.utmaximur.alcoholtracker.data.model.Drink
import com.utmaximur.alcoholtracker.data.model.Icon
import java.io.IOException

class AssetsModule(private val assetManager: AssetManager) {

    fun getIconsList(): List<Icon> {
        val json = getJsonFromAssets(assetManager, "icons_list.json")
        val turnsType = object : TypeToken<List<Icon>>() {}.type
        return Gson().fromJson(json, turnsType)
    }

    fun getDrinkList(): List<Drink> {
        val json = getJsonFromAssets(assetManager, "drink_list.json")
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
}