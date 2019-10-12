package cz.levinzonr.spotistats.network.token

import cz.levinzonr.spotistats.repositories.AuthTokenRepository
import okhttp3.Interceptor
import okhttp3.Response

class AuthTokenInterceptor(private val tokenRepository: AuthTokenRepository) : Interceptor {

    /**
     * Actual method where the interception takes place
     * @param chain current chain of requests
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
                .newBuilder()
        tokenRepository.get()?.let { request.header("Authorization", "Bearer ${it.access_token}") }
        return chain.proceed(request.build())
    }
}