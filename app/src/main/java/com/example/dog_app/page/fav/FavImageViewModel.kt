package com.example.dog_app.page.fav

import androidx.lifecycle.MutableLiveData
import com.example.dog_app.data.preference.LocalPreferenceController
import com.example.dog_app.koin.fragment.FragmentScope
import com.example.dog_app.viewmodel.ObservableViewModel
import javax.inject.Inject

@FragmentScope
class FavImageViewModel @Inject constructor(
    private var localPreferenceController: LocalPreferenceController
) : ObservableViewModel() {

    val showLoadingView = MutableLiveData<Boolean>(false)
    val isFavoriteAvailable = MutableLiveData<Boolean>(false)

    fun getFavoriteImages() : ArrayList<String> {
        val list = localPreferenceController.getFavoriteDog()
        isFavoriteAvailable.postValue(list.isNotEmpty())
        return list
    }

    fun getFavoriteBreeds() : ArrayList<String> {
        val list = localPreferenceController.getFavoriteBreed()
        isFavoriteAvailable.postValue(list.isNotEmpty())
        return list
    }
}