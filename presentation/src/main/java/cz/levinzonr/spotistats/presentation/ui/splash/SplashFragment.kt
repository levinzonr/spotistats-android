package cz.levinzonr.spotistats.presentation.ui.splash

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import cz.levinzonr.spotistats.presentation.R
import cz.levinzonr.spotistats.presentation.extensions.observeNonNull
import cz.levinzonr.spotistats.presentation.ui.base.BaseFragment
import cz.levinzonr.spotistats.presentation.ui.main.*

class SplashFragment : BaseFragment() {

    private val viewModel by viewModel<SplashViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.viewState.observeNonNull(viewLifecycleOwner) { state ->
            handleNStack(state)
            handleNavigation(state)
        }
        viewModel.initAppState()
    }

    private fun handleNStack(state: SplashViewState) {

    }

    private fun handleNavigation(state: SplashViewState) {

    }

    private fun showApp() {
        startActivity(MainActivity.createIntent(requireContext()))
        activity?.overridePendingTransition(0, 0)
    }

    private fun startPlayStore() {
        try {
            startActivity(
                    Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=${context?.packageName}")
                    )
            )
        } catch (anfe: android.content.ActivityNotFoundException) {
            startActivity(
                    Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://play.google.com/store/apps/details?id=${context?.packageName}")
                    )
            )
        }
    }

}