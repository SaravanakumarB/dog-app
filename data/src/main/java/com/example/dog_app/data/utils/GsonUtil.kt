package com.example.dog_app.data.utils

import android.util.Log
import com.example.dog_app.data.BuildConfig
import com.example.dog_app.data.ds.GetAllDogMainResponseDeserializer
import com.example.dog_app.data.response.GetAllDogMainResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class GsonUtil {
  var gson: Gson
  var json: Json

  init {
    val gsonBuilder = GsonBuilder()
    registerTypeAdapters(gsonBuilder)
    gson = gsonBuilder.setLenient().create()
    json = Json { coerceInputValues = true;ignoreUnknownKeys = true }
  }

  private fun registerTypeAdapters(gsonBuilder: GsonBuilder) {

    // Orders
    gsonBuilder.registerTypeAdapter(GetAllDogMainResponse::class.java, GetAllDogMainResponseDeserializer())

  }

  inline fun <reified T> kJsonDeserialization(t: T, jsonElement: JsonElement?): T {
    var obj = t
    try {
      obj = json.decodeFromString(jsonElement.toString())
    } catch (e: Exception) {
      if (BuildConfig.DEBUG) {
        Log.e("Error", e.stackTraceToString())
      }
    }
    return obj
  }

  companion object {
    private var instance: GsonUtil? = null
    fun getInstance(): GsonUtil {
      if (instance == null) {
        instance = GsonUtil()
      }
      return instance!!
    }
  }
}
