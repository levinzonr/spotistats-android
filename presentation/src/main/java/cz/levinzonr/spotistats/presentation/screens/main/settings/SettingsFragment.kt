package cz.levinzonr.spotistats.presentation.screens.main.settings


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.levinzonr.spotistats.models.DarkMode

import cz.levinzonr.spotistats.presentation.R
import cz.levinzonr.spotistats.presentation.base.BaseFragment
import cz.levinzonr.spotistats.presentation.base.BaseViewModel
import cz.levinzonr.spotistats.presentation.extensions.setDarkMode
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : BaseFragment<State>() {

    override val viewModel: SettingsViewModel by viewModel()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        settingsDarkModeBtn.setOnClickListener { viewModel.dispatch(Action.DarkModePreferencePressed) }
        settingsLogoutBtn.setOnClickListener { viewModel.dispatch(Action.LogoutButtonClicked) }
        settingsFeedbackBtn.setOnClickListener { viewModel.dispatch(Action.FeedbackButtonClicked) }
        settingsAboutBtn.setOnClickListener { viewModel.dispatch(Action.AboutButtonClicked) }
    }

    override fun renderState(state: State) {
        settingsDarkModeBtn.value = state.darkMode.name
        setDarkMode(state.darkMode)
        state.showDarkModeDialog?.consume()?.let(this::showSelectDarkModeDialog)

    }

    private fun showSelectDarkModeDialog(darkMode: DarkMode) = AlertDialog.Builder(context)
            .setSingleChoiceItems(DarkMode.values().map { it.name }.toTypedArray(), darkMode.ordinal) { d, i ->
                viewModel.dispatch(Action.DarkModePrefSelected(DarkMode.values()[i]))
                d.dismiss()
            }.show()
}
