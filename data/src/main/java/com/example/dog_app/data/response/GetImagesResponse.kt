package com.example.dog_app.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class GetImagesResponse (
    @SerializedName("status")
    var status: String = "",

    @SerializedName("message")
    var message: ArrayList<String> = ArrayList(),
) : Parcelable