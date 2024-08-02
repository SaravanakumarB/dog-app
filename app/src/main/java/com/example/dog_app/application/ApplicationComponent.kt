package com.example.dog_app.application

import android.app.Application
import com.example.dog_app.data.preference.LocalPreferenceController
import com.example.dog_app.koin.activity.ActivityComponent
import com.example.dog_app.koin.di.DataModule
import com.example.dog_app.koin.di.NetworkModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Provider
import javax.inject.Singleton

/** Root Dagger component for the application. All application-scoped modules should be included in this component. */
@Singleton
@Component(modules = [ApplicationModule::class, DataModule::class, NetworkModule::class])
interface ApplicationComponent {

  @Component.Builder
  interface Builder {
    @BindsInstance
    fun setApplication(application: Application): Builder
    fun build(): ApplicationComponent
  }

  fun getLocal(): LocalPreferenceController

  fun getActivityComponentBuilderProvider(): Provider<ActivityComponent.Builder>
}
