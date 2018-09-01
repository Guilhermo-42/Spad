package duo.com.spad.flow.login

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import duo.com.spad.model.User

/**
 * Presenter class for LoginActivity.
 *
 * @author Guilherme
 * @since 01/09/2018
 */
class LoginPresenterImpl(private var context: Context) {

    companion object {
        private const val EXCEPTION_TAG = "LoginException"
    }

    private var googleSignInClient: GoogleSignInClient? = null

    private var presenter: LoginPresenter? = null

    fun setListener(presenter: LoginPresenter) {
        this.presenter = presenter
    }

    fun configureGoogleSignIn() {
        googleSignInClient = GoogleSignIn.getClient(context,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build())
    }

    fun hasLoggedUser(): Boolean = GoogleSignIn.getLastSignedInAccount(context) != null

    fun trySignIn() {
        googleSignInClient?.let { client -> presenter?.trySignIn(client.signInIntent) }
    }

    fun handleSignInResult(accountTask: Task<GoogleSignInAccount>): User? {
        var user: User? = User()
        try {
            user?.withGoogle(accountTask.getResult(ApiException::class.java))
        } catch (exception: ApiException) {
            user = null
            Log.e(EXCEPTION_TAG, exception.statusCode.toString())
        }

        return user
    }

}