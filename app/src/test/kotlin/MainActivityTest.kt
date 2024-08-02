package com.example.dog_app

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.dog_app.page.MainActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setUp() {

    }

    @Test
    fun checkBreedScreenDisplays() {


        onView(withId(R.id.iv_favourite_icon)).perform(click())

        onView(withId(R.id.rv_breeds)).check(matches(isDisplayed()))

        pressBack()

        onView(withId(R.id.iv_favourite_icon)).check(matches(isDisplayed()))
    }
}