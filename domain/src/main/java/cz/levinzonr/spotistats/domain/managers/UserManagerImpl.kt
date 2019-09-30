package cz.levinzonr.spotistats.domain.managers

import cz.levinzonr.spotistats.repositories.AuthTokenRepository

class UserManagerImpl(private val authTokenRepository: AuthTokenRepository) : UserManager {

    override fun isLoggedIn(): Boolean {
        return authTokenRepository.get() != null
    }

    override fun logout() {
        authTokenRepository.clear()
    }
}