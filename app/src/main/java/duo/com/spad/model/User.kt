package duo.com.spad.model

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import java.io.Serializable

/**
 * @author Guilherme
 * @since 01/09/2018
 */
data class User(

        @Transient
        var googleSignInAccount: GoogleSignInAccount? = null

): Serializable {

    var id: String? = null
    var name: String? = null
    var email: String? = null
    var photoUri: String? = null
    var type: TYPE? = null

    fun withGoogle(googleSignInAccount: GoogleSignInAccount?): User {
        this.googleSignInAccount = googleSignInAccount

        googleSignInAccount?.let { account ->
            id = account.id
            name = account.displayName
            email = account.email
            photoUri = account.photoUrl.toString()
            type = TYPE.GOOGLE
        }

        return this
    }

    enum class TYPE {

        GOOGLE,
        FACEBOOK,
        NONE

    }

}