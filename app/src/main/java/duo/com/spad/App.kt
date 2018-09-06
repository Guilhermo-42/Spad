package duo.com.spad

import android.app.Application
import com.crashlytics.android.Crashlytics
import duo.com.spad.di.components.DaggerPresenterComponent
import duo.com.spad.di.components.PresenterComponent
import duo.com.spad.di.modules.PresenterModule
import io.fabric.sdk.android.Fabric

/**
 * @author Guilherme
 * @since 01/09/2018
 */
open class App : Application() {

    companion object {
        private lateinit var component: PresenterComponent
        fun getPresenterComponent(): PresenterComponent = component
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerPresenterComponent.builder()
                .presenterModule(PresenterModule(this))
                .build()

        initFabric()
    }

    private fun initFabric() {
        Fabric.with(this, Crashlytics())
    }

}