package duo.com.spad.flow.login

import android.content.Intent
import duo.com.spad.model.User

/**
 * @author Guilherme
 * @since 01/09/2018
 */
interface LoginPresenter {

    fun showLoading()

    fun hideLoading()

    fun trySignIn(intent: Intent)

    fun loginSuccess(user: User)

    fun loginFail()

}