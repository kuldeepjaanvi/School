package com.enabling.neeladri.utils

import android.content.Context
import android.util.Log
import com.enabling.neeladri.network.model.Dashboard
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okio.IOException


object CommonMethods {

    fun loadJsonFileData(context: Context): List<Dashboard.Item> {

        var jsonString: String? = null

        try {
            jsonString = context.assets.open("data/data.json")
                .bufferedReader()
                .use { it.readText() }
        } catch (ioException: IOException) {
            Log.e("tag", ioException.toString())
        }

        val listDataType = object : TypeToken<List<Dashboard.Item>>() {}.type
        jsonString
        return Gson().fromJson(jsonString, listDataType)
    }
}