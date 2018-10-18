package duo.com.spad.flow.profile

import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestBuilder

/**
 * @author Guilherme
 * @since 18/10/2018
 */
interface ProfilePresenter {

    fun onLoadingProfilePicture()

    fun onProfilePictureLoaded(builder: RequestBuilder<Drawable>)

    fun onProfilePictureLoadError()

    fun onProfileNameLoaded(currentUserName: String)

    fun onLogoutSuccess()

}