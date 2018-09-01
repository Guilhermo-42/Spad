package duo.com.spad.flow.login

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

/**
 * Presenter class for LoginActivity.
 *
 * @author Guilherme
 * @since 01/09/2018
 */
class LoginPresenter(private var context: Context) {


    fun configureGoogleSignIn() {
        val googleSignIn = GoogleSignIn.getClient(context,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestEmail()
                        .build())
    }

    fun hasLoggedUser(): Boolean = GoogleSignIn.getLastSignedInAccount(context) != null

}