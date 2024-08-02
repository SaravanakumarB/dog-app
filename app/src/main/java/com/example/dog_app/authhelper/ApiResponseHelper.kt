package com.example.dog_app.authhelper

import com.example.dog_app.utils.Constants.Companion.NEGOTIABLE_ERROR
import com.example.dog_app.utils.Constants.Companion.REFRESH_TOKEN_EXPIRED
import com.example.dog_app.utils.Constants.Companion.SUCCESS
import com.example.dog_app.utils.Constants.Companion.UNAUTHORIZED_ACCESS_TOKEN


inline fun handleApiResponse(
  code: Int,
  crossinline doOnSuccess: () -> Unit,
  crossinline doOnError: () -> Unit,
  crossinline doOnNegotiableError: () -> Unit,
  crossinline doOnRefreshTokenExpire: () -> Unit
) {
  when (code) {
    SUCCESS -> doOnSuccess()
    NEGOTIABLE_ERROR -> doOnNegotiableError()
    REFRESH_TOKEN_EXPIRED -> doOnRefreshTokenExpire()
    UNAUTHORIZED_ACCESS_TOKEN -> doOnRefreshTokenExpire()
    else -> doOnError()
  }
}