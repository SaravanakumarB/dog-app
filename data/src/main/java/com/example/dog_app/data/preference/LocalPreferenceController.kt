package com.example.dog_app.data.preference

import com.example.dog_app.data.preference.DogPreference.Companion.KEY_FAVORITE_BREEDS
import com.example.dog_app.data.preference.DogPreference.Companion.KEY_FAVORITE_IMAGES
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class LocalPreferenceController @Inject constructor(
    private val dogPreference: DogPreference
) {
    fun setFavoriteDog(images: ArrayList<String>) {
        val gson = Gson()
        val string = gson.toJson(images).toString()
        dogPreference.saveUserPreference(KEY_FAVORITE_IMAGES, string)
    }

    fun getFavoriteDog(): ArrayList<String> {
        val string = dogPreference.getUserPreference(KEY_FAVORITE_IMAGES) ?: ""

        return if (string.isEmpty()) {
            ArrayList<String>()
        } else {
            val gson = Gson()
            gson.fromJson(string, object : TypeToken<ArrayList<String?>?>() {}.type)
        }
    }

    fun setFavoriteBreed(ids: ArrayList<String>) {
        val gson = Gson()
        val string = gson.toJson(ids).toString()
        dogPreference.saveUserPreference(KEY_FAVORITE_BREEDS, string)
    }

    fun getFavoriteBreed(): ArrayList<String> {
        val string = dogPreference.getUserPreference(KEY_FAVORITE_BREEDS) ?: ""

        return if (string.isEmpty()) {
            ArrayList<String>()
        } else {
            val gson = Gson()
            gson.fromJson(string, object : TypeToken<ArrayList<String?>?>() {}.type)
        }
    }
}