package duo.com.spad.flow.profile

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.RequestBuilder
import duo.com.spad.App
import duo.com.spad.R
import duo.com.spad.flow.login.LoginActivity
import duo.com.spad.ui.UiLoader
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

class ProfileFragment : Fragment(), ProfilePresenter {

    companion object {
        const val TAG = "ProfileFragmentTag"
        fun newInstance(): Fragment = ProfileFragment()
    }

    @Inject
    lateinit var presenter: ProfilePresenterImpl

    init {
        App.getPresenterComponent().inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.setNewPresenter(this)
        presenter.configureGoogleSignIn()
        requireContext().let { presenter.tryRetrieveUser() }

        setListeners()
    }

    override fun onLoadingProfilePicture() {
        imageProgressBar.visibility = View.VISIBLE
    }

    override fun onProfilePictureLoaded(builder: RequestBuilder<Drawable>) {
        profilePhoto?.let {
            builder.into(it)
        }
        imageProgressBar.visibility = View.GONE
    }

    override fun onProfilePictureLoadError() {
        imageProgressBar.visibility = View.GONE
    }

    override fun onProfileNameLoaded(currentUserName: String) {
        profileNameText?.text = currentUserName
    }

    override fun onLogoutSuccess() {
        requireActivity().let {
            UiLoader.goToActivity(it, LoginActivity::class.java)
            it.finishAffinity()
        }
    }

    override fun onLogoutError() {
        requireContext().let {
            Toast.makeText(it, R.string.logout_error_text, Toast.LENGTH_LONG).show()
        }
    }

    private fun setListeners() {
        logoutText.setOnClickListener { presenter.tryLogout() }
    }

}
