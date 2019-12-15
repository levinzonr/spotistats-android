package cz.levinzonr.spoton.presentation.screens.main.settings


import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.levinzonr.spoton.models.DarkMode
import cz.levinzonr.spoton.models.DeviceInfo
import cz.levinzonr.spoton.presentation.R
import cz.levinzonr.spoton.presentation.base.BaseFragment
import cz.levinzonr.spoton.presentation.extensions.setDarkMode
import cz.levinzonr.spoton.presentation.extensions.toUIMessage
import kotlinx.android.synthetic.main.fragment_settings.*
import org.koin.android.viewmodel.ext.android.viewModel
import android.content.Intent
import android.net.Uri




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
        settingsDarkModeBtn.value = state.darkMode.toUIMessage(requireContext())
        setDarkMode(state.darkMode)
        settingsAboutBtn.value = state.versionName
        state.showDarkModeDialog?.consume()?.let(this::showSelectDarkModeDialog)
        state.showFeedbackView?.consume()?.let(this::showFeedbackView)
        state.openBrowser?.consume()?.let(this::openBrowser)

    }

    private fun openBrowser(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
        })
    }

    private fun showSelectDarkModeDialog(darkMode: DarkMode) = AlertDialog.Builder(context)
            .setSingleChoiceItems(DarkMode.values().map { it.toUIMessage(requireContext()) }.toTypedArray(), darkMode.ordinal) { d, i ->
                viewModel.dispatch(Action.DarkModePrefSelected(DarkMode.values()[i]))
                d.dismiss()
            }.show()

    private fun showFeedbackView(deviceInfo: DeviceInfo) {
        val bodyText =  "----------------\nDevice Info: ${deviceInfo.deviceName}\n" +
                "Android Version: ${deviceInfo.deviceOSVersion}\n" +
                "App Info: ${deviceInfo.versionName} (${deviceInfo.versionCode})"

        val mailto = "mailto:levinzon.roman@gmail.com" +
                "?subject=" + Uri.encode(getString(R.string.feedback_subject)) +
                "&body=" + Uri.encode(bodyText)

        val intent = Intent().apply { setData(Uri.parse(mailto)) }
        startActivity(Intent.createChooser(intent, "Send Email"))
    }
}
