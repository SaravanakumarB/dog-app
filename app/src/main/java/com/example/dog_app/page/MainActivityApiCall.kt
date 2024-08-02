package com.example.dog_app.page

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dog_app.R
import com.example.dog_app.authhelper.APIResultListener
import com.example.dog_app.authhelper.BaseAPICall
import com.example.dog_app.authhelper.ErrorEnum
import com.example.dog_app.authhelper.NetworkConnectionUtil
import com.example.dog_app.authhelper.handleApiResponse
import com.example.dog_app.domain.response.GetAllDogMainModel
import com.example.dog_app.domain.response.GetImagesModel
import com.example.dog_app.domain.response.GetRandomImageModel
import com.example.dog_app.domain.scheduler.IScheduler
import com.example.dog_app.domain.usecase.GetAllDogDataRequestUseCase
import com.example.dog_app.domain.usecase.GetBreedImagesRequestUseCase
import com.example.dog_app.domain.usecase.GetRandomImageRequestUseCase
import io.reactivex.SingleObserver
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainActivityApiCall @Inject constructor(
    private val activity: AppCompatActivity,
    private val fragment: Fragment,
    private val scheduler: IScheduler,
    private val networkConnectionUtil: NetworkConnectionUtil,
    private val getAllDogDataRequestUseCase: GetAllDogDataRequestUseCase,
    private val getBreedImagesRequestUseCase: GetBreedImagesRequestUseCase,
    private val getRandomImageRequestUseCase: GetRandomImageRequestUseCase,
) : BaseAPICall() {

    private val apiResultListener = fragment as APIResultListener

    fun getAllDogData() {
        //Note : No need to handle api failure or error, it is background api.
        if (!checkInternet()) {
            return
        }
        apiResultListener.startLoadingView()
        getAllDogDataRequestUseCase.execute(GetAllDogDataRequestUseCase.Params())
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe(object : SingleObserver<GetAllDogMainModel> {

                override fun onSuccess(getAllDogMainModel: GetAllDogMainModel) {
                    apiResultListener.stopLoadingView()
                    handleApiResponse(
                        200,
                        doOnSuccess = {
                            apiResultListener.onAPISuccess(
                                GET_ALL_DOG_DATA,
                                getAllDogMainModel
                            )
                        },
                        doOnError = {
                        },
                        doOnRefreshTokenExpire = {

                        },
                        doOnNegotiableError = {
                        }
                    )
                }

                override fun onSubscribe(d: Disposable) {
                    apiResultListener.onAddRxCall(d)
                }

                override fun onError(e: Throwable) {
                    apiResultListener.stopLoadingView()
                    apiResultListener.onAPIFailure(ErrorEnum.UNEXPECTED_ERROR, e)
                    Log.e(MainActivity.TAG, "onError: $e")
                }
            })
    }

    fun getBreedImageData(breed: String) {
        //Note : No need to handle api failure or error, it is background api.
        if (!checkInternet()) {
            return
        }
        apiResultListener.startLoadingView()
        getBreedImagesRequestUseCase.execute(GetBreedImagesRequestUseCase.Params(breed))
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe(object : SingleObserver<GetImagesModel> {

                override fun onSuccess(getImagesModel: GetImagesModel) {
                    apiResultListener.stopLoadingView()
                    handleApiResponse(
                        200,
                        doOnSuccess = {
                            apiResultListener.onAPISuccess(
                                GET_BREED_IMAGES_DATA,
                                getImagesModel
                            )
                        },
                        doOnError = {
                        },
                        doOnRefreshTokenExpire = {

                        },
                        doOnNegotiableError = {
                        }
                    )
                }

                override fun onSubscribe(d: Disposable) {
                    apiResultListener.onAddRxCall(d)
                }

                override fun onError(e: Throwable) {
                    apiResultListener.stopLoadingView()
                    apiResultListener.onAPIFailure(ErrorEnum.UNEXPECTED_ERROR, e)
                    Log.e(MainActivity.TAG, "onError: $e")
                }
            })
    }

    fun getRandomImageData() {
        //Note : No need to handle api failure or error, it is background api.
        if (!checkInternet()) {
            return
        }
        apiResultListener.startLoadingView()
        getRandomImageRequestUseCase.execute(GetRandomImageRequestUseCase.Params())
            .subscribeOn(scheduler.io())
            .observeOn(scheduler.ui())
            .subscribe(object : SingleObserver<GetRandomImageModel> {

                override fun onSuccess(getRandomImageModel: GetRandomImageModel) {
                    apiResultListener.stopLoadingView()
                    handleApiResponse(
                        200,
                        doOnSuccess = {
                            apiResultListener.onAPISuccess(
                                GET_RANDOM_IMAGES_DATA,
                                getRandomImageModel
                            )
                        },
                        doOnError = {
                        },
                        doOnRefreshTokenExpire = {

                        },
                        doOnNegotiableError = {
                        }
                    )
                }

                override fun onSubscribe(d: Disposable) {
                    apiResultListener.onAddRxCall(d)
                }

                override fun onError(e: Throwable) {
                    apiResultListener.stopLoadingView()
                    apiResultListener.onAPIFailure(ErrorEnum.UNEXPECTED_ERROR, e)
                    Log.e(MainActivity.TAG, "onError: $e")
                }
            })
    }
    private fun checkInternet(): Boolean {
        if (networkConnectionUtil.getCurrentConnectionStatus() == NetworkConnectionUtil.ConnectionStatus.NONE) {
            Toast.makeText(
                activity,
                fragment.getString(R.string.internet_connection_error),
                Toast.LENGTH_SHORT
            ).show()
            apiResultListener.stopLoadingView()
            return false
        }
        return true
    }

    companion object {
        internal const val GET_ALL_DOG_DATA = "GET_ALL_DOG_DATA"
        internal const val GET_BREED_IMAGES_DATA = "GET_BREED_IMAGES_DATA"
        internal const val GET_RANDOM_IMAGES_DATA = "GET_RANDOM_IMAGES_DATA"
    }
}