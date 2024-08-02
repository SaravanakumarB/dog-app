package com.example.dog_app.application

import android.app.Application
import android.content.Context
import com.example.dog_app.koin.activity.ActivityComponent
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/** Provides core infrastructure needed to support all other dependencies in the app. */
@Module(subcomponents = [ActivityComponent::class])
@InstallIn(SingletonComponent::class)
class ApplicationModule {
  @Provides
  @Singleton
  @ApplicationContext
  fun provideApplicationContext(application: Application): Context {
    return application
  }

  @Provides
  @Singleton
  fun provideContext(@ApplicationContext context: Context): Context {
    return context
  }
}
