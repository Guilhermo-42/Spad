package duo.com.spad.flow.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import duo.com.spad.R
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var loginPresenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPresenter.configureGoogleSignIn()
    }

}
