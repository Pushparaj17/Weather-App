package com.pushparaj.samples.weatherapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.pushparaj.samples.weatherapp.viewmodel.WeatherViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class MainActivityTest {
    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Rule @JvmField
    var activityScenarioRule = ActivityScenarioRule(
        MainActivity::class.java
    )

    @Before
    public fun setup() {
        hiltAndroidRule.inject()
    }

    @Test
    fun test_serachView_visibleOnLaunch() {
        activityScenarioRule.scenario.onActivity { activity: MainActivity ->
            onView(withId(R.id.searchView)).check(matches(ViewMatchers.isDisplayed()));
        }
    }

    @Test
    fun test_loadingState_enableProgressBar() {
        val viewState = WeatherViewModel.ViewState.Loading
        activityScenarioRule.scenario.onActivity { activity: MainActivity ->
             activity.updateUI(viewState)
             onView(withId(R.id.pBLoading)).check(matches(ViewMatchers.isDisplayed()));
        }
    }

    @Test
    fun test_errorState_enableErrorView() {
        val viewState = WeatherViewModel.ViewState.Loading
        activityScenarioRule.scenario.onActivity { activity: MainActivity ->
            activity.updateUI(viewState)
            onView(withId(R.id.tvErrorMessage)).check(matches(ViewMatchers.isDisplayed()));
        }
    }
}