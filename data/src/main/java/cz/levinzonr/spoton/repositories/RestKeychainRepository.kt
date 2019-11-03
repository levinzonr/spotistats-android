package cz.levinzonr.spoton.repositories

import cz.levinzonr.spoton.models.AccessToken
import cz.levinzonr.spoton.models.SpotifyCredentials
import cz.levinzonr.spoton.network.AuthApi
import org.koin.core.KoinComponent

class RestKeychainRepository(
        private val api: AuthApi,
        private val credentials: SpotifyCredentials,
        private val authTokenRepository: AuthTokenRepository)
    : KeychainRepository, KoinComponent {


    override suspend fun obtainAccessToken(code: String): AccessToken {
        val token =  api.obtainAccessTokenAsync(code, credentials.clientId, credentials.clientSecret).await()
        authTokenRepository.set(token)
        return token
    }
}