package com.example.dog_app.domain.datasources

import com.example.dog_app.domain.response.GetAllDogMainModel
import com.example.dog_app.domain.response.GetImagesModel
import com.example.dog_app.domain.response.GetRandomImageModel
import io.reactivex.Single

interface DogDataSources {
    fun getAllDogDataRequest(): Single<GetAllDogMainModel>

    fun getBreedsImages(breed: String) : Single<GetImagesModel>

    fun getRandomImage() : Single<GetRandomImageModel>
}