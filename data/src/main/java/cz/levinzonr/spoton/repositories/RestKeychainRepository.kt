package cz.levinzonr.spoton.repositories

import cz.levinzonr.spoton.models.AccessToken
import cz.levinzonr.spoton.network.SpotifyAuthApi
import org.koin.core.KoinComponent

class RestKeychainRepository(
        private val api: SpotifyAuthApi,
        private val clientId: String,
        private val clientSecret: String)
    : KeychainRepository, KoinComponent {


    override suspend fun obtainAccessToken(code: String): AccessToken {
        return api.obtainAccessTokenAsync(code, clientId, clientSecret).await()
    }
}