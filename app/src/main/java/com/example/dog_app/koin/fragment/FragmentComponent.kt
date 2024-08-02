package com.example.dog_app.koin.fragment

import androidx.fragment.app.Fragment
import com.example.dog_app.koin.view.ViewComponent
import com.example.dog_app.page.breed.BreedsFragment
import com.example.dog_app.page.fav.FavBreedFragment
import com.example.dog_app.page.fav.FavImageFragment
import com.example.dog_app.page.images.DogImageFragment
import com.example.dog_app.page.splash.SplashFragment
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Provider

/** Root subcomponent for all fragments. */
@Subcomponent(modules = [FragmentModule::class])
@FragmentScope
interface FragmentComponent {
  @Subcomponent.Builder
  interface Builder {
    @BindsInstance
    fun setFragment(fragment: Fragment): Builder

    fun build(): FragmentComponent
  }

  fun getViewComponentBuilderProvider(): Provider<ViewComponent.Builder>

  fun inject(breedsFragment: BreedsFragment)
  fun inject(dogImageFragment: DogImageFragment)
  fun inject(favImageFragment: FavImageFragment)
  fun inject(favBreedFragment: FavBreedFragment)
  fun inject(splashFragment: SplashFragment)
}
