package com.example.dog_app.page.splash

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.dog_app.authhelper.APIResultListener
import com.example.dog_app.authhelper.ErrorEnum
import com.example.dog_app.domain.usecase.BaseParams
import com.example.dog_app.fragment.InjectableFragment
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class SplashFragment : InjectableFragment(), APIResultListener {

    @Inject
    lateinit var splashFragmentPresenter: SplashFragmentPresenter
    companion object {
        internal const val TAG = "SplashFragment.tag"
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
        return splashFragmentPresenter.handleOnCreate(inflater, container)
    }

    override fun onPause() {
        super.onPause()
        setNetworkChangeListener(null)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroyView() {
        splashFragmentPresenter.handleOnDestroyView()
        super.onDestroyView()
    }

    override fun onAddRxCall(d: Disposable) {
        splashFragmentPresenter.handleAddRxCall(d)
    }

    override fun onAPIFailure(errorEnum: ErrorEnum, error: Throwable?) {
        splashFragmentPresenter.handleAPIFailure(errorEnum, error)
    }

    override fun onAPISuccess(resultType: String, result: Any?) {
        splashFragmentPresenter.handleAPISuccess(resultType, result)
    }

    override fun onClearAllCalls() {
        // Not useful for this feature
    }

    override fun onServerFailure(failureData: HashMap<String, Any>) {
        // Not useful for this feature
    }

    override fun startLoadingView() {
        splashFragmentPresenter.handleStartLoadingView()
    }

    override fun stopLoadingView() {
        splashFragmentPresenter.handleStopLoadingView()
    }

    override fun onRefreshTokenExpired() {
        splashFragmentPresenter.logout()
    }

    override fun onAccessTokenResultSuccess(apiType: String, params: BaseParams?) {
    }
}