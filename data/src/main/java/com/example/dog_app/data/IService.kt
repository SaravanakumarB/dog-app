package com.example.dog_app.data

import com.example.dog_app.data.response.GetAllDogMainResponse
import com.example.dog_app.data.response.GetImagesResponse
import com.example.dog_app.data.response.GetRandomImageResponse
import io.reactivex.Single
import retrofit2.http.*

// Main retrofit service calls
interface IService {
  @GET("breeds/list/all")
  fun getAllDogList(): Single<GetAllDogMainResponse>

  @GET("breed/{breed}/images")
  fun getBreedImages(@Path("breed") breed: String): Single<GetImagesResponse>

  @GET("breeds/image/random")
  fun getRandomImage(): Single<GetRandomImageResponse>
}
