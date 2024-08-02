package com.example.dog_app.domain.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetAllDogMainModel(
    var status: String = "",
    var message: DogBreedModel,
) : Parcelable