package cz.levinzonr.spotistats.presentation.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ww.roxie.BaseState
import cz.levinzonr.spotistats.presentation.extensions.getSharedViewModel
import cz.levinzonr.spotistats.presentation.extensions.getViewModel
import cz.levinzonr.spotistats.presentation.extensions.lifecycleAwareLazy
import cz.levinzonr.spotistats.presentation.extensions.observeNonNull
import cz.levinzonr.spotistats.presentation.navigation.Route
import cz.levinzonr.spotistats.presentation.util.ViewErrorController

abstract class BaseFragment<S: BaseState> : Fragment() {

    abstract val viewModel: BaseViewModel<*,*, S>


    abstract fun renderState(state: S)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.observableState.observeNonNull(viewLifecycleOwner) {
            renderState(it)
        }

        viewModel.navigationLiveData.observeNonNull(viewLifecycleOwner) {
            it.consume()?.let(this::handleNavigationEvent)
        }
    }


    protected fun handleNavigationEvent(route: Route) {

    }
}
