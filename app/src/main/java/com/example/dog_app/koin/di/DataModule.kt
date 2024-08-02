package com.example.dog_app.koin.di

import com.example.dog_app.data.IService
import com.example.dog_app.data.repositories.DogDataRepository
import com.example.dog_app.data.schedulers.SchedulerProvider
import com.example.dog_app.domain.datasources.DogDataSources
import com.example.dog_app.domain.scheduler.IScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

  @Provides
  @Singleton
  fun provideChatDataSource(service: IService): DogDataSources {
    return DogDataRepository(service)
  }

  @Provides
  @Singleton
  fun provideScheduler(): IScheduler {
    return SchedulerProvider.instance
  }
}