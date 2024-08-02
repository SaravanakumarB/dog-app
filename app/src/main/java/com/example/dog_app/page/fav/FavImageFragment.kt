package com.example.dog_app.page.fav

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dog_app.authhelper.ErrorEnum
import com.example.dog_app.domain.usecase.BaseParams
import com.example.dog_app.fragment.InjectableFragment
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class FavImageFragment : InjectableFragment() {

    @Inject
    lateinit var favImageFragmentPresenter: FavImageFragmentPresenter
    companion object {
        internal const val TAG = "FavImageFragment.tag"
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
        return favImageFragmentPresenter.handleOnCreate(inflater, container)
    }

    override fun onPause() {
        super.onPause()
        setNetworkChangeListener(null)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        favImageFragmentPresenter.handleOnDestroyView()
        super.onDestroyView()
    }
}