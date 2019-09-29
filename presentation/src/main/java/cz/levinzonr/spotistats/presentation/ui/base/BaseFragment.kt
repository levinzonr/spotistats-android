package cz.levinzonr.spotistats.presentation.ui.base

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ww.roxie.BaseState
import dagger.android.support.DaggerFragment
import cz.levinzonr.spotistats.presentation.extensions.getSharedViewModel
import cz.levinzonr.spotistats.presentation.extensions.getViewModel
import cz.levinzonr.spotistats.presentation.extensions.lifecycleAwareLazy
import cz.levinzonr.spotistats.presentation.extensions.observeNonNull
import cz.levinzonr.spotistats.presentation.navigation.Route
import cz.levinzonr.spotistats.presentation.util.ViewErrorController
import javax.inject.Inject

abstract class BaseFragment<S: BaseState> : DaggerFragment() {

    abstract val viewModel: BaseViewModel<*,*, S>

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject lateinit var defaultErrorController: dagger.Lazy<ViewErrorController>

    protected inline fun <reified VM : ViewModel> getViewModel(): VM =
        getViewModel(viewModelFactory)

    protected inline fun <reified VM : ViewModel> getSharedViewModel(): VM =
        getSharedViewModel(viewModelFactory)

    protected inline fun <reified VM : ViewModel> viewModel(): Lazy<VM> = lifecycleAwareLazy(this) {
        getViewModel<VM>()
    }

    protected inline fun <reified VM : ViewModel> sharedViewModel(): Lazy<VM> =
        lifecycleAwareLazy(this) {
            getSharedViewModel<VM>()
        }


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
