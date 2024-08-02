package com.example.dog_app.domain.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Error(
  var code: Int = 0,
  var message: String = "",
  var respId: String = ""
) : Parcelable
