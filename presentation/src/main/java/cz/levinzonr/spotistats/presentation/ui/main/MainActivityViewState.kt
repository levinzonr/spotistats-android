package cz.levinzonr.spotistats.presentation.ui.main

import cz.levinzonr.spotistats.presentation.util.SingleEvent

data class MainActivityViewState(
    val errorMessage: SingleEvent<String>? = null,
    val isLoading: Boolean = false
)