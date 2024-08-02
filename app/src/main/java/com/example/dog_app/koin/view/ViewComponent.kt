package com.example.dog_app.koin.view

import android.view.View
import dagger.BindsInstance
import dagger.Subcomponent

/** Root subcomponent for custom views. */
@Subcomponent
@ViewScope
interface ViewComponent {
  @Subcomponent.Builder
  interface Builder {
    @BindsInstance
    fun setView(view: View): Builder

    fun build(): ViewComponent
  }

}
