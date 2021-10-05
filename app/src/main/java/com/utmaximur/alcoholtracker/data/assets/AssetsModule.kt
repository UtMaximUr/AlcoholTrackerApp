package com.utmaximur.alcoholtracker.data.assets

import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.utmaximur.alcoholtracker.data.dbo.DrinkDBO
import com.utmaximur.alcoholtracker.data.dbo.IconDBO
import java.io.IOException

class AssetsModule(private val assetManager: AssetManager) {

    fun getIconsList(): List<IconDBO> {
        val json = getJsonFromAssets(assetManager, "icons_list.json")
        val turnsType = object : TypeToken<List<IconDBO>>() {}.type
        return Gson().fromJson(json, turnsType)
    }

    fun getDrinkList(): List<DrinkDBO> {
        val json = getJsonFromAssets(assetManager, "drink_list.json")
        val turnsType = object : TypeToken<List<DrinkDBO>>() {}.type
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