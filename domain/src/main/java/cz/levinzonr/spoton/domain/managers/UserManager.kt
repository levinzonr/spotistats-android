package cz.levinzonr.spoton.domain.managers

interface UserManager {
    fun isLoggedIn() : Boolean
    fun logout()
}