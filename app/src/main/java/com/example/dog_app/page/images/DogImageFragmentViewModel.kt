package com.example.dog_app.page.images

import androidx.lifecycle.MutableLiveData
import com.example.dog_app.data.preference.LocalPreferenceController
import com.example.dog_app.koin.fragment.FragmentScope
import com.example.dog_app.viewmodel.ObservableViewModel
import javax.inject.Inject

@FragmentScope
class DogImageFragmentViewModel @Inject constructor(
    private var localPreferenceController: LocalPreferenceController
) : ObservableViewModel() {

    val showLoadingView = MutableLiveData<Boolean>(false)

    fun addToFavorite(image: String) {
        val list = localPreferenceController.getFavoriteDog()
        if(list.isEmpty()) {
            list.add(image)
            localPreferenceController.setFavoriteDog(list)
        } else {
            if(!list.contains(image)) {
                list.add(image)
                localPreferenceController.setFavoriteDog(list)
            }
        }
    }
}