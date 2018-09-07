package duo.com.spad.model.database

import duo.com.spad.model.User
import java.io.Serializable

/**
 * @author Guilherme
 * @since 06/09/2018
 */
object DatabaseUser : Serializable {

    var id: String? = null
    var name: String? = null
    var email: String? = null
    var photoUri: String? = null
    var type: User.TYPE? = null

    fun withUser(user: User): DatabaseUser {
        this.id = user.id
        this.name = user.name
        this.email = user.email
        this.photoUri = user.photoUri
        this.type = user.type

        return this
    }

}