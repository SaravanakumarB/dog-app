package com.example.dog_app.koin.activity

import com.example.dog_app.koin.fragment.FragmentComponent
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** Root activity module. */
@Module(subcomponents = [FragmentComponent::class]) @InstallIn(SingletonComponent::class) class ActivityModule
