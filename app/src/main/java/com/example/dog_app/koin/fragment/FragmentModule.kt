package com.example.dog_app.koin.fragment

import com.example.dog_app.koin.view.ViewComponent
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/** Root fragment module. */
@Module(subcomponents = [ViewComponent::class]) @InstallIn(SingletonComponent::class) class FragmentModule
