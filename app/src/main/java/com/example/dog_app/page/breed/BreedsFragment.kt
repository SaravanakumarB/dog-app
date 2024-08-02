package com.example.dog_app.page.breed

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dog_app.fragment.InjectableFragment
import com.example.dog_app.koin.fragment.FragmentScope
import javax.inject.Inject

@FragmentScope
class BreedsFragment : InjectableFragment() {

    @Inject
    lateinit var breedsFragmentPresenter: BreedsFragmentPresenter
    companion object {
        internal const val TAG = "BreedsFragment.tag"
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
        return breedsFragmentPresenter.handleOnCreate(inflater, container)
    }

    override fun onPause() {
        super.onPause()
        setNetworkChangeListener(null)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        breedsFragmentPresenter.handleOnDestroy()
        super.onDestroyView()
    }
}