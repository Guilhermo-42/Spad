package duo.com.spad.flow.profile

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import duo.com.spad.R
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment : Fragment() {

    companion object {
        const val TAG = "ProfileFragmentTag"
        fun newInstance(): Fragment = ProfileFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        FirebaseAuth.getInstance()
                .currentUser
                ?.let {
                    profileNameText?.text = it.displayName

                    profilePhoto?.let { image ->
                        Glide.with(requireContext())
                                .load(it.photoUrl)
                                .apply(RequestOptions.circleCropTransform())
                                .into(image)
                    }
                }
    }

}
