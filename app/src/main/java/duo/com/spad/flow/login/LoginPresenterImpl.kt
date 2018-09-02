package duo.com.spad.flow.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import duo.com.spad.R
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

    private val firebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    private var googleSignInClient: GoogleSignInClient? = null

    private var presenter: LoginPresenter? = null

    fun setListener(presenter: LoginPresenter) {
        this.presenter = presenter
    }

    fun configureGoogleSignIn() {
        googleSignInClient = GoogleSignIn.getClient(context,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(context.getString(R.string.google_sign_in_client_id))
                        .requestEmail()
                        .build())
    }

    fun hasLoggedUser(): Boolean {
        val account = GoogleSignIn.getLastSignedInAccount(context)
        return account != null && !account.isExpired
    }

    fun userLogged() {
        presenter?.loginSuccess(User()
                .withGoogle(GoogleSignIn.getLastSignedInAccount(context))
                .withFirebase(firebaseAuth.currentUser))
    }

    fun trySignIn() {
        googleSignInClient?.let { client -> presenter?.trySignIn(client.signInIntent) }
    }

    fun getSignedInUser(): User = User().withGoogle(GoogleSignIn.getLastSignedInAccount(context))
            .withFirebase(firebaseAuth.currentUser)

    fun handleSignInResult(accountTask: Task<GoogleSignInAccount>) {
        presenter?.showLoading()
        val user: User? = User()
        try {
            val account = accountTask.getResult(ApiException::class.java)
            user?.withGoogle(account)
            user?.let { authWithFirebase(account, it) }
        } catch (exception: ApiException) {
            googleSignInClient?.signOut()
            firebaseAuth.signOut()
            presenter?.loginFail()
            presenter?.hideLoading()
            Log.e(EXCEPTION_TAG, exception.statusCode.toString())
        }
    }

    private fun authWithFirebase(googleSignInAccount: GoogleSignInAccount, user: User) {
        firebaseAuth.signInWithCredential(getGoogleCredential(googleSignInAccount))
                .addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        user.withFirebase(firebaseAuth.currentUser)
                        presenter?.loginSuccess(user)
                        presenter?.hideLoading()
                    } else {
                        googleSignInClient?.signOut()
                        firebaseAuth.signOut()
                        presenter?.loginFail()
                        presenter?.hideLoading()
                    }
                }
    }

    private fun getGoogleCredential(googleSignInAccount: GoogleSignInAccount) =
            GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)

}