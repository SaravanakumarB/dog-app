package com.example.dog_app.data

import com.example.dog_app.data.response.GetAllDogMainResponse
import com.example.dog_app.data.response.GetImagesResponse
import com.example.dog_app.data.response.GetRandomImageResponse
import io.reactivex.Single
import retrofit2.http.*

// Main retrofit service calls
interface IService {
  @GET(GET_ALL_BREED_URL)
  fun getAllDogList(): Single<GetAllDogMainResponse>

  @GET(GET_BREED_IMAGES)
  fun getBreedImages(@Path("breed") breed: String): Single<GetImagesResponse>

  @GET(GET_RANDOM_BREED_IMAGE)
  fun getRandomImage(): Single<GetRandomImageResponse>

  companion object {
    const val GET_ALL_BREED_URL = "breeds/list/all"
    const val GET_BREED_IMAGES = "breed/{breed}/images"
    const val GET_RANDOM_BREED_IMAGE = "breeds/image/random"
  }
}
