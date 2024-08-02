package com.example.dog_app.domain.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetRandomImageModel(
    var status: String = "",
    var message: String = "",
) : Parcelable