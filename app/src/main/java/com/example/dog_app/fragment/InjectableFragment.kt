package com.example.dog_app.fragment

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.dog_app.activity.CONNECTIVITY_ACTION
import com.example.dog_app.activity.InjectableAppCompatActivity
import com.example.dog_app.authhelper.NetworkConnectionUtil
import com.example.dog_app.errorhandling.RetrofitException
import com.example.dog_app.koin.fragment.FragmentComponent

/**
 * A fragment that facilitates field injection to children. This fragment can only be used with
 * [InjectableAppCompatActivity] contexts.
 */
abstract class InjectableFragment : Fragment(), OnInternetConnectionListener {
  /**
   * The [FragmentComponent] corresponding to this fragment. This cannot be used before [onAttach] is called, and can be
   * used to inject lateinit fields in child fragments during fragment attachment (which is recommended to be done in an
   * override of [onAttach]).
   */
  lateinit var fragmentComponent: FragmentComponent

  private var alertDialog: AlertDialog? = null

  private var networkChangeListener: INetworkChangeListener? = null
  private var networkChangeReceiver: NetworkChangeReceiver? = null
  private var networkConnectionUtil: NetworkConnectionUtil? = null


  override fun onAttach(context: Context) {
    super.onAttach(context)
    networkChangeReceiver = NetworkChangeReceiver()
    networkConnectionUtil = NetworkConnectionUtil(requireContext())
    fragmentComponent =
      (requireActivity() as InjectableAppCompatActivity).createFragmentComponent(this)
  }

  private fun registerNetworkChangeReceiver() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
      val intentFilter = IntentFilter()
      intentFilter.addAction(CONNECTIVITY_ACTION)
      requireActivity().registerReceiver(networkChangeReceiver, intentFilter)
    }
  }

  private fun unregisterNetworkChangeReceiver() {
    try {
      requireActivity().unregisterReceiver(networkChangeReceiver)
    } catch (e: IllegalArgumentException) {
      e.printStackTrace()
    }
  }

  inner class NetworkChangeReceiver() : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
      checkConnection()
    }
  }

  private fun checkConnection() {
    if (networkConnectionUtil?.getCurrentConnectionStatus() != NetworkConnectionUtil.ConnectionStatus.NONE) {
      networkChangeListener?.networkConnected()
      if (alertDialog != null && alertDialog!!.isShowing) {
        alertDialog?.dismiss()
      }
    } else {
      networkChangeListener?.networkDisconnected()
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return super.onCreateView(inflater, container, savedInstanceState)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }

  override fun onResume() {
    super.onResume()
    registerNetworkChangeReceiver()
  }

  override fun onRetry() {
    checkConnection()
  }

  override fun onPause() {
    super.onPause()
  }

  fun isNetworkAvailable(): Boolean {
    return networkConnectionUtil?.getCurrentConnectionStatus() != NetworkConnectionUtil.ConnectionStatus.NONE
  }

  fun handleNetworkErrorPage(error: Throwable?): Boolean {
    if (error is RetrofitException) {
      if (error.code == RetrofitException.CODE_1000 || !isNetworkAvailable()) {
        return true
      }
    }
    return false
  }

  override fun onDestroy() {
    super.onDestroy()
    unregisterNetworkChangeReceiver()
  }

  fun setNetworkChangeListener(listener: INetworkChangeListener?) {
    this.networkChangeListener = listener
  }

  interface INetworkChangeListener {
    fun networkConnected()
    fun networkDisconnected()
  }

  fun handlePage(visibility: Boolean = false) {

  }

  fun hideKeyBoard(activity: AppCompatActivity, view: View) {
    val imm: InputMethodManager =
      activity.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
  }
}

interface OnInternetConnectionListener {
  fun onRetry()
}

