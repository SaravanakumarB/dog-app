package com.example.dog_app.application

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import androidx.multidex.MultiDexApplication
import com.example.dog_app.koin.activity.ActivityComponent


/** The root [Application] of the app. */
class DogApplication : MultiDexApplication() {
    /** The root [ApplicationComponent]. */
    private val component: ApplicationComponent by lazy {
        DaggerApplicationComponent.builder()
            .setApplication(this)
            .build()
    }

    /**
     * Returns a new [ActivityComponent] for the specified activity. This should only be used by
     * [InjectableAppCompatActivity].
     */
    fun createActivityComponent(activity: AppCompatActivity): ActivityComponent {
        return component.getActivityComponentBuilderProvider().get().setActivity(activity).build()
    }

    override fun onCreate() {
        super.onCreate()
        appContext = this
    }

    companion object {
        lateinit var appContext: DogApplication
        var isInBackground = true
        private const val TAG = "DogApplication"
    }
}