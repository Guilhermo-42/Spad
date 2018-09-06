package duo.com.spad.flow.splash

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import duo.com.spad.App
import duo.com.spad.R
import duo.com.spad.flow.list.ListActivity
import duo.com.spad.flow.login.LoginActivity
import duo.com.spad.flow.login.LoginPresenterImpl
import duo.com.spad.flow.main.MainFlowActivity
import duo.com.spad.ui.UiLoader
import javax.inject.Inject

class SplashScreenActivity : AppCompatActivity() {

    companion object {
        private const val ANIMATION_DELAY_MILLS = 3000L
    }

    @Inject
    lateinit var loginPresenterImpl: LoginPresenterImpl

    init {
        App.getPresenterComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        //TODO Call updateUi after splash animation ends
        Handler().postDelayed({
            updateUi()
        }, ANIMATION_DELAY_MILLS)

    }

    private fun updateUi() {
        if (loginPresenterImpl.hasLoggedUser()) {
            val bundle = Bundle()
            bundle.putSerializable(ListActivity.USER_EXTRA, loginPresenterImpl.getSignedInUser())
            UiLoader.goToActivityWithData(this, MainFlowActivity::class.java, bundle)
        } else {
            UiLoader.goToActivity(this, LoginActivity::class.java)
        }
        finish()
    }
}
