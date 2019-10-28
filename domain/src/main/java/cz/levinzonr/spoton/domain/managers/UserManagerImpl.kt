package cz.levinzonr.spoton.domain.managers

import cz.levinzonr.spoton.repositories.AuthTokenRepository

class UserManagerImpl(private val authTokenRepository: AuthTokenRepository) : UserManager {

    override fun isLoggedIn(): Boolean {
        return authTokenRepository.get() != null
    }

    override fun logout() {
        authTokenRepository.clear()
    }
}