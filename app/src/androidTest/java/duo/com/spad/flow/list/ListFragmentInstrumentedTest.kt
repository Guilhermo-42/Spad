package duo.com.spad.flow.list

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.filters.MediumTest
import android.support.test.filters.SmallTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import duo.com.spad.R
import duo.com.spad.flow.category.CategoryListAdapter
import duo.com.spad.flow.main.MainFlowActivity
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @author Guilherme
 * @since 23/09/2018
 */

@RunWith(AndroidJUnit4::class)
class ListFragmentInstrumentedTest {

    companion object {
        private const val TITLE_TO_INPUT = "Instrumented Test on Spad app"
        private const val DESCRIPTION_TO_INPUT = "Instrumented Test on Spad app description text"
    }

    @get:Rule
    var mainFlowRule: ActivityTestRule<MainFlowActivity> = ActivityTestRule(MainFlowActivity::class.java)

    @Before
    fun init() {
        onView(withId(R.id.navigation_list))
                .perform(click())
    }

    @Test
    @SmallTest
    fun click_on_fab_and_tries_to_write_a_new_note() {
        onView(withId(R.id.listScreenFab))
                .perform(click())

        onView(withId(R.id.titleInputText))
                .perform(typeText(TITLE_TO_INPUT))

        onView(withId(R.id.descriptionInputText))
                .perform(typeText(DESCRIPTION_TO_INPUT))
                .perform(closeSoftKeyboard())

        onView(withId(R.id.lowPriorityToggle))
                .perform(click())
                .check(matches(isChecked()))
    }

    @Test
    @SmallTest
    fun tries_to_select_a_category() {
        onView(withId(R.id.listScreenFab))
                .perform(click())

        onView(withId(R.id.titleInputText))
                .perform(typeText(TITLE_TO_INPUT))

        onView(withId(R.id.descriptionInputText))
                .perform(typeText(DESCRIPTION_TO_INPUT))
                .perform(closeSoftKeyboard())

        onView(withId(R.id.highPriorityToggle))
                .perform(click())
                .check(matches(isChecked()))

        onView(withId(R.id.addItemConfirm))
                .perform(click())

        onView(withId(R.id.categoryList))
                .perform(RecyclerViewActions.actionOnItemAtPosition<CategoryListAdapter.ViewHolder>(0, click()))

    }

    @Test
    @SmallTest
    fun click_on_fab_and_fails_to_write_a_new_note() {
        onView(withId(R.id.listScreenFab))
                .perform(click())

        onView(withId(R.id.titleInputText))
                .perform(typeText(TITLE_TO_INPUT))

        onView(withId(R.id.descriptionInputText))
                .perform(typeText(DESCRIPTION_TO_INPUT))
                .perform(closeSoftKeyboard())

        onView(withId(R.id.addItemConfirm))
                .perform(click())

        onView(withId(R.id.priorityError))
                .check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    @MediumTest
    fun check_text_inputs() {
        onView(withId(R.id.listScreenFab))
                .perform(click())

        onView(withId(R.id.titleInputText))
                .perform(typeText(TITLE_TO_INPUT))
                .perform(typeText(TITLE_TO_INPUT))
                .perform(typeText(TITLE_TO_INPUT))
                .perform(typeText(TITLE_TO_INPUT))
                .perform(typeText(TITLE_TO_INPUT))

        onView(withId(R.id.descriptionInputText))
                .perform(typeText(DESCRIPTION_TO_INPUT))
                .perform(typeText(DESCRIPTION_TO_INPUT))
                .perform(typeText(DESCRIPTION_TO_INPUT))
                .perform(typeText(DESCRIPTION_TO_INPUT))
                .perform(typeText(DESCRIPTION_TO_INPUT))
                .perform(typeText(DESCRIPTION_TO_INPUT))
                .perform(typeText(DESCRIPTION_TO_INPUT))
                .perform(typeText(DESCRIPTION_TO_INPUT))
                .perform(closeSoftKeyboard())

    }

}