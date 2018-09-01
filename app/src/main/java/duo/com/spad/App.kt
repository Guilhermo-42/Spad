package duo.com.spad

import android.app.Application
import duo.com.spad.di.components.DaggerPresenterComponent
import duo.com.spad.di.components.PresenterComponent
import duo.com.spad.di.modules.PresenterModule

/**
 * @author Guilherme
 * @since 01/09/2018
 */
class App : Application() {

    val component: PresenterComponent by lazy {
        DaggerPresenterComponent.builder()
                .presenterModule(PresenterModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
    }

}