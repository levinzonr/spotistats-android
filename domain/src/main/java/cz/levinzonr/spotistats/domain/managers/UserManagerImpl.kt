package cz.levinzonr.spotistats.domain.managers

class UserManagerImpl : UserManager {

    override fun isLoggedIn(): Boolean {
        return false
    }
}