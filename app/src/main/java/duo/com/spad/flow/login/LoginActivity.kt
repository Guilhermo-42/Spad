package duo.com.spad.flow.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.SignInButton
import duo.com.spad.App
import duo.com.spad.R
import duo.com.spad.flow.list.ListActivity
import duo.com.spad.model.User
import duo.com.spad.ui.UiLoader
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LoginPresenter {

    companion object {
        private const val SIGN_IN_REQUEST_CODE = 989
    }

    @Inject
    lateinit var loginPresenterImpl: LoginPresenterImpl

    init {
        App.getPresenterComponent().inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginPresenterImpl.setListener(this)
        loginPresenterImpl.configureGoogleSignIn()
        if (loginPresenterImpl.hasLoggedUser()) {
            updateUi(loginPresenterImpl.getLoggedUser())
        }

        initializeViews()

        setListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            updateUi(loginPresenterImpl.handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data)))
        }
    }

    override fun trySignIn(intent: Intent) {
        startActivityForResult(intent, SIGN_IN_REQUEST_CODE)
    }

    private fun initializeViews() {
        googleSignInButton.setSize(SignInButton.SIZE_STANDARD)
    }

    private fun setListeners() {
        googleSignInButton.setOnClickListener { loginPresenterImpl.trySignIn() }
    }

    private fun updateUi(user: User?) {
        user?.let {
            val bundle = Bundle()
            bundle.putSerializable(ListActivity.USER_EXTRA, it)
            UiLoader.goToActivityWithData(this, ListActivity::class.java, bundle)
            finish()
        }
    }

}