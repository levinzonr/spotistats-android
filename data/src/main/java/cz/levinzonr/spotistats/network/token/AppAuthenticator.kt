package cz.levinzonr.spotistats.network.token

import cz.levinzonr.spotistats.network.AuthApi
import cz.levinzonr.spotistats.repositories.AuthTokenRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.koin.core.KoinComponent


class AppAuthenticator(
        private val authTokenRepository: AuthTokenRepository,
        private val authApi: AuthApi,
        private val clientId: String,
        private val clientSecret: String
) : Authenticator, KoinComponent {


    override fun authenticate(route: Route?, response: Response): Request? {
        val token = authTokenRepository.get() ?: return null
        val refreshed = authApi.refreshAccessToken(token.refresh_token, clientId, clientSecret).execute()
        if (refreshed.isSuccessful) {
            val newToken = refreshed.body() ?: return null
            authTokenRepository.set(newToken)
            return response.request().newBuilder()
                    .header("Authorization", "Bearer: ${newToken.access_token}")
                    .build()

        } else {
            return null
        }

    }

}