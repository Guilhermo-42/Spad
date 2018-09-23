package duo.com.spad.flow.login

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
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
class LoginActivityInstrumentedTest {

    @get:Rule
    var loginRule: ActivityTestRule<LoginActivity> = ActivityTestRule(LoginActivity::class.java)

    @Test
    fun click_on_sign_in_button_calls_login_screen() {
        onView(withId(R.id.googleSignInButton))
                .perform(click())
    }

}