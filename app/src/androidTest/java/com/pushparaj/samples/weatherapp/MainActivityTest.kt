package com.pushparaj.samples.weatherapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun test_serachView_visibleOnLaunch() {
        onView(withId(R.id.searchView)).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    fun test_tvCityName_visibleOnLaunch() {
        onView(withId(R.id.tvCityName)).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    fun test_tvTemperature_visibleOnLaunch() {
        onView(withId(R.id.tvTemperature)).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    fun test_tvDescription_visibleOnLaunch() {
        onView(withId(R.id.tvDescription)).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    fun test_pBLoading_inVisibleOnLaunch() {
        onView(withId(R.id.pBLoading)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
    }
}