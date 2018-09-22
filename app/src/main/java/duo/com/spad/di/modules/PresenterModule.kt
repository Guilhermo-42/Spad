package duo.com.spad.di.modules

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import duo.com.spad.flow.list.add.AddItemPresenterImpl
import duo.com.spad.flow.login.LoginPresenterImpl
import javax.inject.Singleton

/**
 * Module responsible for provides presenters instances.
 *
 * @author Guilherme
 * @since 01/09/2018
 */
@Module
class PresenterModule(private val application: Application) {

    @Provides
    @Singleton
    fun providesContext(): Context = application.applicationContext

    @Provides
    fun providesLoginPresenter(context: Context): LoginPresenterImpl = LoginPresenterImpl(context)

    @Provides
    fun providesAddItemPresenter(): AddItemPresenterImpl = AddItemPresenterImpl()

}