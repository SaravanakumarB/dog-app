package com.example.dog_app.domain.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GetImagesModel(
    var status: String = "",
    var message: ArrayList<String> = arrayListOf(),
) : Parcelable