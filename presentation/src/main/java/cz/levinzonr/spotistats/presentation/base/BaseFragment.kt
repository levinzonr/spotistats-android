package cz.levinzonr.spotistats.presentation.base

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ww.roxie.BaseState
import cz.levinzonr.spotistats.presentation.extensions.observeNonNull
import cz.levinzonr.spotistats.presentation.navigation.Route
import cz.levinzonr.spotistats.presentation.ui.main.MainActivity
import cz.levinzonr.spotistats.presentation.ui.onboarding.OnboardingActivity
import timber.log.Timber

abstract class BaseFragment<S: BaseState> : Fragment() {

    abstract val viewModel: BaseViewModel<*,*, S>


    abstract fun renderState(state: S)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.observableState.observeNonNull(viewLifecycleOwner) {
            Timber.d("${viewModel.javaClass.simpleName} state: $it")
            renderState(it)
        }

        viewModel.navigationLiveData.observeNonNull(viewLifecycleOwner) {
            Timber.d("Navigation Event: $it")
            it.consume()?.let(this::handleNavigationEvent)
        }
    }


    protected fun handleNavigationEvent(route: Route) {
        when(route) {
            is Route.Destination -> findNavController().navigate(route.navDirections)
            is Route.Main -> {
                activity?.finish()
                startActivity(MainActivity.createIntent(requireContext()))
            }
            is Route.Onboarding -> {
                activity?.finish()
                startActivity(Intent(context, OnboardingActivity::class.java))
            }
        }
    }


}
