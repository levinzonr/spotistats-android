package cz.levinzonr.spoton.domain.managers

import com.spotify.sdk.android.authentication.AuthenticationClient
import cz.levinzonr.spoton.repositories.AuthTokenRepository

class UserManagerImpl(private val authTokenRepository: AuthTokenRepository) : UserManager {

    override fun isLoggedIn(): Boolean {
        return authTokenRepository.accessToken != null
    }

    override fun logout() {
        authTokenRepository.clear()
    }
}