package cz.levinzonr.spotistats.presentation.screens.main.profile


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import coil.api.load
import coil.transform.CircleCropTransformation
import cz.levinzonr.spotistats.models.UserResponse

import cz.levinzonr.spotistats.presentation.R
import cz.levinzonr.spotistats.presentation.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : BaseFragment<State>() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override val viewModel: ProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userLogoutBtn.setOnClickListener { viewModel.dispatch(Action.LogoutPressed) }
        settingsBtn.setOnClickListener { viewModel.dispatch(Action.SettingsPressed) }
        /*switchCompat.isChecked = (activity as AppCompatActivity).delegate?.localNightMode == AppCompatDelegate.MODE_NIGHT_YES
        switchCompat.setOnCheckedChangeListener { compoundButton, b ->
            val newMode = if (b) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            (activity as AppCompatActivity).delegate.localNightMode = newMode
        }*/
    }

    override fun renderState(state: State) {
        userLogoutBtn.isEnabled = !state.isLoading
        state.user?.let(this::renderProfile)
    }

    private fun renderProfile(user: UserResponse) {
        userDisplayNameTv.text = user.display_name
        userAvaterIv.load(user.images.firstOrNull()?.url) {
            transformations(CircleCropTransformation())
        }
    }
}
