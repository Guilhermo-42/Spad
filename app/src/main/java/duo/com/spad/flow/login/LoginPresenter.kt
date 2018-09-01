package duo.com.spad.flow.login

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

/**
 * @author Guilherme
 * @since 01/09/2018
 */
interface LoginPresenter {

    fun trySignIn(intent: Intent)

}