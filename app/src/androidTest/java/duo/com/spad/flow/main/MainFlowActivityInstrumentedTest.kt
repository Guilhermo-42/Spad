package duo.com.spad.flow.main

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import duo.com.spad.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Guilherme
 * @since 23/09/2018
 */

@RunWith(AndroidJUnit4::class)
class MainFlowActivityInstrumentedTest {

    @get:Rule
    var mainFlowRule: ActivityTestRule<MainFlowActivity> = ActivityTestRule(MainFlowActivity::class.java)

    @Test
    fun click_on_navigation_list_calls_list_screen() {
        onView(withId(R.id.navigation_list))
                .perform(click())

        onView(withId(R.id.listRoot))
                .check(matches(isDisplayed()))
    }

    @Test
    fun click_on_navigation_profile_calls_profile_screen() {
        onView(withId(R.id.navigation_profile))
                .perform(click())

        onView(withId(R.id.profileRoot))
                .check(matches(isDisplayed()))
    }

}