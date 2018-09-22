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
import duo.com.spad.model.note.Category
import duo.com.spad.model.note.Note
import duo.com.spad.model.note.Priority
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
                        saveUserToDatabase(user)
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

    private fun saveUserToDatabase(user: User) {
        val database = FirebaseFirestore.getInstance()

        //TODO REMOVE THIS MOCK
        user.notes = listOf(
                Note("Nota 1",
                        "Description text bem grandinho mesmo",
                        Priority.LOW,
                        Category("Comida", R.drawable.ic_default_category_family)),
                Note("Nota 2 text",
                        "Description text bem grandinho mesmo",
                        Priority.HIGH,
                        Category("Comida", R.drawable.ic_default_category_recreation)),
                Note("Nota 3 huasuhsahusahuas",
                        "Description text bem grandinho mesmo 12312903",
                        Priority.MEDIUM,
                        Category("Comida", R.drawable.ic_default_category_house)),
                Note("Nota 4 titulo",
                        "Description text bem grandinho mesmo",
                        Priority.URGENT,
                        Category("Comida", R.drawable.ic_default_category_food))
        )

        user.email?.let {
            database.collection(DatabaseCollections.USERS.description)
                .document(it)
                    .set(DatabaseUser.withUser(user))
        }
    }

    private fun getGoogleCredential(googleSignInAccount: GoogleSignInAccount) =
            GoogleAuthProvider.getCredential(googleSignInAccount.idToken, null)

}