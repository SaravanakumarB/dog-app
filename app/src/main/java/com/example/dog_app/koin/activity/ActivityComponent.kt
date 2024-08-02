package com.example.dog_app.koin.activity

import androidx.appcompat.app.AppCompatActivity
import com.example.dog_app.koin.fragment.FragmentComponent
import com.example.dog_app.page.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Provider

@Subcomponent(modules = [ActivityModule::class])
@ActivityScope
interface ActivityComponent {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun setActivity(appCompatActivity: AppCompatActivity): Builder

        fun build(): ActivityComponent
    }

    fun getFragmentComponentBuilderProvider(): Provider<FragmentComponent.Builder>

    fun inject(homeActivity: MainActivity)
}