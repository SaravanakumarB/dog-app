package com.example.dog_app.page

import android.app.Application
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.example.dog_app.R
import com.example.dog_app.activity.InjectableAppCompatActivity
import com.example.dog_app.authhelper.NetworkConnectionUtil
import com.example.dog_app.databinding.ActivityMainBinding
import com.example.dog_app.viewmodel.ViewModelProvider
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class MainActivity : InjectableAppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        internal const val TAG = "MainActivity.tag"
    }

    private var isConnected = true
    private fun setConnectivityListener() {
        setNetworkChangeListener(object : INetworkChangeListener {
            override fun networkConnected() {
                if (!isConnected) {
                    isConnected = true
                }
            }

            override fun networkDisconnected() {
                isConnected = false
                showNoInternetConnectionDialog(true)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.let {
            it.lifecycleOwner = this
        }
        setNavGraph()
    }

    private fun setNavGraph() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.main_nav_host_container) as NavHostFragment
        val navController = navHostFragment.navController

        val navInflater = navController.navInflater
        val graph = navInflater.inflate(R.navigation.main_nav_graph)
        graph.setStartDestination(R.id.splashFragment)
        navController.graph = graph
    }

    override fun onPause() {
        super.onPause()
        setNetworkChangeListener(null)
    }

    override fun onResume() {
        super.onResume()
        setConnectivityListener()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}