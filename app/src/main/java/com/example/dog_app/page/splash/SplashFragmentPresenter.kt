package com.example.dog_app.page.splash

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.dog_app.R
import com.example.dog_app.authhelper.ErrorEnum
import com.example.dog_app.data.preference.LocalPreferenceController
import com.example.dog_app.databinding.FragmentDogImageBinding
import com.example.dog_app.databinding.FragmentSplashBinding
import com.example.dog_app.domain.response.GetImagesModel
import com.example.dog_app.domain.response.GetRandomImageModel
import com.example.dog_app.errorhandling.RetrofitException
import com.example.dog_app.koin.fragment.FragmentScope
import com.example.dog_app.page.MainActivityApiCall
import com.example.dog_app.page.images.DogImageFragmentViewModel
import com.example.dog_app.page.images.ImageListAdapter
import com.example.dog_app.presenter.BasePresenter
import com.example.dog_app.utils.Constants
import com.example.dog_app.viewmodel.ViewModelProvider
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.delay
import javax.inject.Inject

@FragmentScope
class SplashFragmentPresenter @Inject constructor(
    private val activity: AppCompatActivity,
    private val fragment: Fragment,
    private val viewModelProvider: ViewModelProvider<SplashViewModel>,
): BasePresenter() {

    @Inject
    lateinit var mainActivityApiCall: MainActivityApiCall

    private lateinit var binding: FragmentSplashBinding
    private lateinit var viewModel: SplashViewModel

    fun handleOnCreate(inflater: LayoutInflater, container: ViewGroup?
    ): View? {
        binding =
            FragmentSplashBinding.inflate(inflater, container, /* attachToRoot= */ false)

        viewModel =
            viewModelProvider.getForFragment(fragment, SplashViewModel::class.java)

        binding.let {
            it.lifecycleOwner = fragment
            it.viewModel = viewModel
        }
        setUI()
        return binding.root
    }

    private fun setUI() {
        if(viewModel.isFavoriteBreedOrImageAvailable()) {
            if(viewModel.isFavoriteImageAvailable()) {
                setImage(viewModel.getFavoriteDog())
            } else {
                mainActivityApiCall.getBreedImageData(viewModel.getFavoriteBreed())
            }
        } else {
            mainActivityApiCall.getRandomImageData()
        }

        activity.lifecycleScope.launchWhenCreated {
            delay(4000)
            fragment.findNavController().navigate(
                R.id.action_splashFragment_to_breedListFragment,
            )
        }
    }

    private fun setImage(image: String) {
        Glide.with(activity).load(image).into(binding.ivDog)
    }

    fun handleStartLoadingView() {
        viewModel.showLoadingView.postValue(true)
    }

    fun handleStopLoadingView() {
        viewModel.showLoadingView.postValue(false)
    }

    fun handleOnDestroyView() {
        unsubscribe()
    }

    override fun unsubscribe() {
        clearAllCalls()
        viewModel.clearAllCalls()
    }

    override fun logout() {

    }

    fun handleAPISuccess(resultType: String, result: Any?) {
        when (resultType) {
            MainActivityApiCall.GET_BREED_IMAGES_DATA -> {
                viewModel.showLoadingView.postValue(false)
                val list = (result as GetImagesModel).message
                if(list.isNotEmpty()) {
                    setImage(list[0])
                }
            }
            MainActivityApiCall.GET_RANDOM_IMAGES_DATA -> {
                viewModel.showLoadingView.postValue(false)
                val image = (result as GetRandomImageModel).message
                if(image.isNotEmpty()) {
                    setImage(image)
                }
            }
        }
    }

    fun handleAddRxCall(d: Disposable) {
        viewModel.addRxCall(d)
    }

    fun handleAPIFailure(
        errorEnum: ErrorEnum, error: Throwable?
    ) {
        viewModel.showLoadingView.postValue(false)
        if (error is RetrofitException) {
            if (error.code == Constants.REFRESH_TOKEN_EXPIRED) {
                logout()
            } else {
                handleError(errorEnum, error)
            }
        } else {
            handleError(errorEnum, error)
        }
    }

    private fun handleError(errorEnum: ErrorEnum, error: Throwable?) {
        when (errorEnum) {
            ErrorEnum.UNEXPECTED_ERROR -> {
                if (error != null) {

                } else {

                }
            }
            else -> {
            }
        }
    }
}