package com.example.dog_app.data.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class GetRandomImageResponse (
    @SerializedName("status")
    var status: String = "",

    @SerializedName("message")
    var message: String = "",
) : Parcelable