package duo.com.spad.model

import android.net.Uri
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

/**
 * @author Guilherme
 * @since 01/09/2018
 */
data class User(

        var googleSignInAccount: GoogleSignInAccount? = null
) {

    private var id: String? = null
    private var name: String? = null
    private var email: String? = null
    private var photoUri: Uri? = null

    fun withGoogle(googleSignInAccount: GoogleSignInAccount?): User {
        this.googleSignInAccount = googleSignInAccount

        googleSignInAccount?.let { account ->
            id = account.id
            name = account.displayName
            email = account.email
            photoUri = account.photoUrl
        }

        return this
    }

}