package cz.levinzonr.spotistats.presentation.ui.splash

import androidx.lifecycle.viewModelScope
import cz.levinzonr.spotistats.presentation.ui.base.BaseViewModel
import cz.levinzonr.spotistats.presentation.util.SingleEvent
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject

class SplashViewModel@Inject constructor() : BaseViewModel<SplashViewState>() {

    override val initState: SplashViewState = SplashViewState(doneLoading = false)

    fun initAppState() = viewModelScope.launch {
        Timber.d("initAppState() - start")
        // Other API calls that might be needed
        // ...
        // Splash should be shown for min. x milliseconds
        val deferredMinDelay = async(Dispatchers.IO) { delay(2000) }

        // Parallel execution, wait on both to finish
        deferredMinDelay.await()

        Timber.d("initAppState() - end")
    }
}