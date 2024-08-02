package com.example.dog_app.authhelper

import com.example.dog_app.domain.usecase.BaseParams
import io.reactivex.disposables.Disposable

interface APIResultListener {

  fun onAddRxCall(d: Disposable)

  fun onAPIFailure(errorEnum: ErrorEnum, error: Throwable?)

  fun onAPISuccess(resultType: String, result: Any?)

  fun onClearAllCalls()

  fun onServerFailure(failureData: HashMap<String, Any>)

  fun startLoadingView()

  fun stopLoadingView()

  fun onRefreshTokenExpired()

  fun onAccessTokenResultSuccess(apiType: String, params: BaseParams?)
}
