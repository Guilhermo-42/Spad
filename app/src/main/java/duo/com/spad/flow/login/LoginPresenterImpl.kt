package duo.com.spad.flow.login

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import duo.com.spad.R
import duo.com.spad.model.User
import duo.com.spad.model.database.DatabaseUser
import duo.com.spad.network.DatabaseCollections

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
                        .requestIdToken(context.getString(R.string.google_client_id))
                        .requestEmail()
                        .build())
    }

    fun hasLoggedUser(): Boolean {
        val account = firebaseAuth.currentUser
        return account != null
    }

    fun userLogged() {
        presenter?.loginSuccess(User()
                .withGoogle(GoogleSignIn.getLastSignedInAccount(context))
                .withFirebase(firebaseAuth.currentUser))
    }

    fun trySignInWithGoogle() {
        googleSignInClient?.let { client -> presenter?.trySignIn(client.signInIntent) }
    }

    fun getSignedInUser(): User = User().withGoogle(GoogleSignIn.getLastSignedInAccount(context))
            .withFirebase(firebaseAuth.currentUser)

    fun handleGoogleSignInIntent(accountTask: Task<GoogleSignInAccount>) {
        presenter?.showLoading()
        val user: User? = User()
        try {
            val account = accountTask.getResult(ApiException::class.java)
            user?.withGoogle(account)
            user?.let { authWithFirebase(account, it) }
        } catch (exception: ApiException) {
            loginFailed()
            Log.e(EXCEPTION_TAG, exception.statusCode.toString())
        }
    }

    private fun authWithFirebase(googleSignInAccount: GoogleSignInAccount, user: User) {
        firebaseAuth.signInWithCredential(getGoogleCredential(googleSignInAccount))
                .addOnCompleteListener { result ->
                    if (result.isSuccessful) {
                        user.withFirebase(firebaseAuth.currentUser)
                        verifyShouldSaveUser(user)
                        presenter?.hideLoading()
                    } else {
                        loginFailed()
                    }
                }
    }

    private fun loginFailed() {
        googleSignInClient?.signOut()
        firebaseAuth.signOut()
        presenter?.loginFail()
        presenter?.hideLoading()
    }

    private fun verifyShouldSaveUser(user: User) {
        user.email?.let {
            FirebaseFirestore.getInstance()
                    .collection(DatabaseCollections.USERS.description)
                    .document(it)
                    .get()
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            if (task.result.exists()) {
                                presenter?.loginSuccess(user)
                            } else {
                                saveUserToDatabase(user)
                            }
                        } else {
                            loginFailed()
                        }
                    }
        }
    }

    private fun saveUserToDatabase(user: User) {
        val database = FirebaseFirestore.getInstance()
        user.email?.let {
            database.collection(DatabaseCollections.USERS.description)
                    .document(it)
                    .set(DatabaseUser.withUser(user))
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            presenter?.loginSuccess(user)
                        } else {
                            loginFailed()
                        }
                    }
        }
    }

    private fun getGoogleCredential(googleSignInAccount: GoogleSignInAccount) =
            GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)

}