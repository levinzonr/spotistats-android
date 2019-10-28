package cz.levinzonr.spoton.presentation.screens.main

import cz.levinzonr.spoton.presentation.util.SingleEvent

data class MainActivityViewState(
    val errorMessage: SingleEvent<String>? = null,
    val isLoading: Boolean = false
)