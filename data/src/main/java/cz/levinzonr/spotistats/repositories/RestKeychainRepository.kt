package cz.levinzonr.spotistats.repositories

import cz.levinzonr.spotistats.models.AccessToken
import cz.levinzonr.spotistats.network.AuthApi
import org.koin.core.KoinComponent

class RestKeychainRepository(
        private val api: AuthApi,
        private val clientId: String,
        private val clientSecret: String)
    : KeychainRepository, KoinComponent {


    override suspend fun obtainAccessToken(code: String): AccessToken {
        return api.obtainAccessTokenAsync(code, clientId, clientSecret).await()
    }
}