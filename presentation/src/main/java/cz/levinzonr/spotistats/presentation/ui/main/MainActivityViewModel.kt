package cz.levinzonr.spotistats.presentation.ui.main

import androidx.lifecycle.viewModelScope
import cz.levinzonr.spotistats.presentation.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainActivityViewModel @Inject constructor() : BaseViewModel<MainActivityViewState>() {
    override val initState: MainActivityViewState = MainActivityViewState()

    fun checkNStack() = viewModelScope.launch {
        // Delay popup a bit so it's not super intrusive
        withContext(Dispatchers.IO) { delay(1000) }
    }
}