package com.example.dog_app.page.fav

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dog_app.R
import com.example.dog_app.fragment.InjectableFragment
import com.example.dog_app.koin.fragment.FragmentScope
import com.example.dog_app.page.breed.BreedsFragmentPresenter
import javax.inject.Inject

@FragmentScope
class FavBreedFragment : InjectableFragment() {

    @Inject
    lateinit var favBreedFragmentPresenter: FavBreedFragmentPresenter
    companion object {
        internal const val TAG = "FavBreedFragment.tag"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return favBreedFragmentPresenter.handleOnCreate(inflater, container)
    }

    override fun onPause() {
        super.onPause()
        setNetworkChangeListener(null)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        favBreedFragmentPresenter.handleOnDestroyView()
        super.onDestroyView()
    }
}
