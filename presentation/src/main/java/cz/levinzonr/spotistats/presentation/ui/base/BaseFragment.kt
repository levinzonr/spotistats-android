package cz.levinzonr.spotistats.presentation.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import cz.levinzonr.spotistats.presentation.extensions.getSharedViewModel
import cz.levinzonr.spotistats.presentation.extensions.getViewModel
import cz.levinzonr.spotistats.presentation.extensions.lifecycleAwareLazy
import cz.levinzonr.spotistats.presentation.util.ViewErrorController
import javax.inject.Inject

abstract class BaseFragment : DaggerFragment() {

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
}
