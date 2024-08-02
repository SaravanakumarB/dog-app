package com.example.dog_app.page.breed

import androidx.lifecycle.MutableLiveData
import com.example.dog_app.data.preference.LocalPreferenceController
import com.example.dog_app.koin.fragment.FragmentScope
import com.example.dog_app.viewmodel.ObservableViewModel
import javax.inject.Inject

@FragmentScope
class BreedsFragmentViewModel @Inject constructor(
    private val localPreferenceController: LocalPreferenceController
) : ObservableViewModel() {

    val showLoadingView = MutableLiveData<Boolean>(false)


    fun getListOfBreeds() : ArrayList<String> {
        val listOfAllBreeds = arrayListOf<String>()
        listOfAllBreeds.also {
            it.add("affenpinscher")
            it.add("african")
            it.add("airedale")
            it.add("akita")
            it.add("appenzeller")
            it.add("australian")
            it.add("basenji")
            it.add("beagle")
            it.add("bluetick")
            it.add("borzoi")
            it.add("bouvier")
            it.add("boxer")
            it.add("brabancon")
            it.add("briard")
            it.add("buhund")
            it.add("bulldog")
            it.add("bullterrier")
            it.add("cattledog")
            it.add("cavapoo")
            it.add("chihuahua")
            it.add("chow")
            it.add("clumber")
            it.add("cockapoo")
            it.add("collie")
            it.add("coonhound")
            it.add("corgi")
            it.add("cotondetulear")
            it.add("dachshund")
            it.add("dalmatian")
            it.add("dane")
            it.add("danish")
            it.add("deerhound")
            it.add("dhole")
            it.add("dingo")
            it.add("doberman")
            it.add("elkhound")
            it.add("entlebucher")
            it.add("eskimo")
            it.add("finnish")
            it.add("frise")
            it.add("gaddi")
            it.add("germanshepherd")
            it.add("greyhound")
            it.add("groenendael")
            it.add("havanese")
            it.add("hound")
            it.add("husky")
            it.add("keeshond")
            it.add("kelpie")
            it.add("kombai")
            it.add("komondor")
            it.add("kuvasz")
            it.add("labradoodle")
            it.add("labrador")
            it.add("lhasa")
            it.add("malamute")
            it.add("malinois")
            it.add("mastiff")
            it.add("mexicanhairless")
            it.add("mix")
            it.add("mountain")
            it.add("mudhol")
            it.add("newfoundland")
            it.add("otterhound")
            it.add("ovcharka")
            it.add("papillon")
            it.add("pariah")
            it.add("pekinese")
            it.add("pembroke")
            it.add("pinscher")
            it.add("pitbull")
            it.add("pointer")
            it.add("pomeranian")
            it.add("poodle")
            it.add("pug")
            it.add("puggle")
            it.add("pyrenees")
            it.add("rajapalayam")
            it.add("redbone")
            it.add("retriever")
            it.add("ridgeback")
            it.add("rottweiler")
            it.add("saluki")
            it.add("samoyed")
            it.add("schipperke")
            it.add("schnauzer")
            it.add("segugio")
            it.add("setter")
            it.add("sharpei")
            it.add("sheepdog")
            it.add("shiba")
            it.add("shihtzu")
            it.add("spitz")
            it.add("springer")
            it.add("stbernard")
            it.add("terrier")
            it.add("tervuren")
            it.add("vizsla")
            it.add("waterdog")
            it.add("weimaraner")
            it.add("whippet")
            it.add("wolfhound")
        }
        return listOfAllBreeds
    }

    fun addToFavorite(image: String) {
        val list = localPreferenceController.getFavoriteBreed()
        if(list.isEmpty()) {
            list.add(image)
            localPreferenceController.setFavoriteBreed(list)
        } else {
            if(!list.contains(image)) {
                list.add(image)
                localPreferenceController.setFavoriteBreed(list)
            } else {
                list.remove(image)
                localPreferenceController.setFavoriteBreed(list)
            }
        }
    }
    

    fun unsubscribe() {
        clearAllCalls()
    }
}