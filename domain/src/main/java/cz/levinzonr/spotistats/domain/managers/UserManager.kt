package cz.levinzonr.spotistats.domain.managers

interface UserManager {
    fun isLoggedIn() : Boolean
    fun logout()
}