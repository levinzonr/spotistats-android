package cz.levinzonr.spoton.network.token

import android.util.Base64
import cz.levinzonr.spoton.network.AuthApi
import cz.levinzonr.spoton.repositories.AuthTokenRepository
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
        val refreshToken = authTokenRepository.refreshToken ?: return null
        val encoded = Base64.encode("$clientId:$clientSecret".toByteArray(), Base64.NO_WRAP)
        val header = "Basic ${String(encoded)}"
        val refreshed = authApi.refreshAccessToken(refreshToken, header).execute()
        if (refreshed.isSuccessful) {
            val newToken = refreshed.body() ?: return null
            println("Body: $newToken")
            authTokenRepository.set(newToken)
            return response.request().newBuilder()
                    .header("Authorization", "Bearer ${newToken.access_token}")
                    .build()

        } else {
            return null
        }

    }

}