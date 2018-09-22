package duo.com.spad.model

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import duo.com.spad.model.note.Note
import java.io.Serializable
import java.util.*

/**
 * @author Guilherme
 * @since 01/09/2018
 */
data class User(

        @Transient
        var googleSignInAccount: GoogleSignInAccount? = null

) : Serializable {

    var id: String? = null
    var name: String? = null
    var email: String? = null
    var photoUri: String? = null
    var type: TYPE? = null
    var notes: LinkedList<Note>? = null

    fun withGoogle(googleSignInAccount: GoogleSignInAccount?): User {
        this.googleSignInAccount = googleSignInAccount

        googleSignInAccount?.let { _ ->
            type = TYPE.GOOGLE
        }

        return this
    }

    fun withFirebase(currentUser: FirebaseUser?): User {
        currentUser?.let { firebaseUser ->
            this.id = firebaseUser.uid
            this.name = firebaseUser.displayName
            this.email = firebaseUser.email
            this.photoUri = firebaseUser.photoUrl.toString()
        }

        return this
    }

    enum class TYPE {

        GOOGLE,
        FACEBOOK,
        NONE

    }

}