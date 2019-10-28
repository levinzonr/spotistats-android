package cz.levinzonr.spoton.presentation.navigation

import androidx.navigation.NavDirections

sealed class Route {

    object Main : Route()
    object Onboarding: Route()

    data class Destination(val navDirections: NavDirections) : Route()
}