package duo.com.spad.flow.profile

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth

/**
 * @author Guilherme
 * @since 18/10/2018
 */
class ProfilePresenterImpl {

    private var presenter: ProfilePresenter? = null

    fun setNewPresenter(presenter: ProfilePresenter) {
        this.presenter = presenter
    }

    fun tryRetrieveUser(context: Context) {
        presenter?.onLoadingProfilePicture()

        FirebaseAuth.getInstance().currentUser?.let { user ->
            user.displayName?.let { presenter?.onProfileNameLoaded(it) }

            Glide.with(context)
                    .load(user.photoUrl)
                    .apply(RequestOptions.circleCropTransform()).let {
                        presenter?.onProfilePictureLoaded(it)
                    }

        }.run { presenter?.onProfilePictureLoadError() }
    }

    fun tryLogout() {
        FirebaseAuth.getInstance().signOut()
        presenter?.onLogoutSuccess()
    }

}