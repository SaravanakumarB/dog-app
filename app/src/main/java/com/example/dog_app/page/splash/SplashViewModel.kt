package com.example.dog_app.page.splash
import androidx.lifecycle.MutableLiveData
import com.example.dog_app.data.preference.LocalPreferenceController
import com.example.dog_app.koin.fragment.FragmentScope
import com.example.dog_app.viewmodel.ObservableViewModel
import javax.inject.Inject

@FragmentScope
class SplashViewModel @Inject constructor(
    private var localPreferenceController: LocalPreferenceController
) : ObservableViewModel() {
    val showLoadingView = MutableLiveData<Boolean>(false)

    fun isFavoriteBreedOrImageAvailable() : Boolean {
        if(localPreferenceController.getFavoriteBreed().isNotEmpty() || localPreferenceController.getFavoriteDog().isNotEmpty()) {
            return true
        }
        return false
    }

    fun isFavoriteImageAvailable() : Boolean {
        if(localPreferenceController.getFavoriteDog().isNotEmpty()) {
            return true
        }
        return false
    }

    fun getFavoriteBreed(): String {
        if(localPreferenceController.getFavoriteBreed().isNotEmpty()) {
            return localPreferenceController.getFavoriteBreed()[0]
        }
        return ""
    }

    fun getFavoriteDog(): String {
        if(localPreferenceController.getFavoriteDog().isNotEmpty()) {
            return localPreferenceController.getFavoriteDog()[0]
        }
        return ""
    }
}