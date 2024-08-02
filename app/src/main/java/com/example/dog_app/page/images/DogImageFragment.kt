package com.example.dog_app.page.images

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dog_app.R
import com.example.dog_app.authhelper.APIResultListener
import com.example.dog_app.authhelper.ErrorEnum
import com.example.dog_app.domain.usecase.BaseParams
import com.example.dog_app.fragment.InjectableFragment
import com.example.dog_app.koin.fragment.FragmentScope
import com.example.dog_app.page.breed.BreedsFragmentPresenter
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class DogImageFragment : InjectableFragment(), APIResultListener {

    @Inject
    lateinit var dogImageFragmentPresenter: DogImageFragmentPresenter
    companion object {
        internal const val TAG = "DogImageFragment.tag"
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
        val breed =
            checkNotNull(arguments?.getString("breed")) {
                "Expected OrderId to be passed to DogImageFragment"
            }
        return dogImageFragmentPresenter.handleOnCreate(inflater, container, breed)
    }

    override fun onPause() {
        super.onPause()
        setNetworkChangeListener(null)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        dogImageFragmentPresenter.handleOnDestroyView()
        super.onDestroyView()
    }

    override fun onAddRxCall(d: Disposable) {
        dogImageFragmentPresenter.handleAddRxCall(d)
    }

    override fun onAPIFailure(errorEnum: ErrorEnum, error: Throwable?) {
        dogImageFragmentPresenter.handleAPIFailure(errorEnum, error)
    }

    override fun onAPISuccess(resultType: String, result: Any?) {
        dogImageFragmentPresenter.handleAPISuccess(resultType, result)
    }

    override fun onClearAllCalls() {
        // Not useful for this feature
    }

    override fun onServerFailure(failureData: HashMap<String, Any>) {
        // Not useful for this feature
    }

    override fun startLoadingView() {
        dogImageFragmentPresenter.handleStartLoadingView()
    }

    override fun stopLoadingView() {
        dogImageFragmentPresenter.handleStopLoadingView()
    }

    override fun onRefreshTokenExpired() {
        dogImageFragmentPresenter.logout()
    }

    override fun onAccessTokenResultSuccess(apiType: String, params: BaseParams?) {
    }
}