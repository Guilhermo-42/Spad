package duo.com.spad.di.components

import dagger.Component
import duo.com.spad.App
import duo.com.spad.di.modules.PresenterModule
import duo.com.spad.flow.login.LoginActivity
import javax.inject.Singleton

/**
 * Component responsible for the presenter module.
 *
 * @author Guilherme
 * @since 01/09/2018
 */
@Singleton
@Component(modules = [PresenterModule::class])
interface PresenterComponent {

    fun inject(app: App)
    fun inject(loginActivity: LoginActivity)

}