package duo.com.spad.flow.profile

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import duo.com.spad.R

/**
 * @author Guilherme
 * @since 18/10/2018
 */
class ProfilePresenterImpl(var context: Context) {

    private var presenter: ProfilePresenter? = null

    private var googleSignInClient: GoogleSignInClient? = null

    fun setNewPresenter(presenter: ProfilePresenter) {
        this.presenter = presenter
    }

    fun tryRetrieveUser() {
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
        googleSignInClient?.signOut()?.addOnCompleteListener {
            if (it.isSuccessful) {
                FirebaseAuth.getInstance().signOut()
                presenter?.onLogoutSuccess()
            } else {
                presenter?.onLogoutError()
            }
        }
    }

    fun configureGoogleSignIn() {
        googleSignInClient = GoogleSignIn.getClient(context,
                GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(context.getString(R.string.google_client_id))
                        .requestEmail()
                        .build())
    }

}