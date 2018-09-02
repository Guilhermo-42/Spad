package duo.com.spad.flow.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
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
        private const val GOOGLE_SIGN_IN_REQUEST_CODE = 989
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
            loginPresenterImpl.userLogged()
        }

        initializeViews()

        setListeners()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            loginPresenterImpl.handleGoogleSignInIntent(GoogleSignIn.getSignedInAccountFromIntent(data))
        }
    }

    override fun trySignIn(intent: Intent) {
        startActivityForResult(intent, GOOGLE_SIGN_IN_REQUEST_CODE)
    }

    override fun loginSuccess(user: User) {
        user.let {
            val bundle = Bundle()
            bundle.putSerializable(ListActivity.USER_EXTRA, it)
            UiLoader.goToActivityWithData(this, ListActivity::class.java, bundle)
            finish()
        }
    }

    override fun loginFail() {
        Toast.makeText(this, R.string.sign_in_fail, Toast.LENGTH_LONG).show()
    }

    override fun showLoading() {
        loginLoadingBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loginLoadingBar.visibility = View.GONE
    }


    private fun initializeViews() {
        googleSignInButton.setSize(SignInButton.SIZE_STANDARD)
    }

    private fun setListeners() {
        googleSignInButton.setOnClickListener { loginPresenterImpl.trySignInWithGoogle() }
    }

}
