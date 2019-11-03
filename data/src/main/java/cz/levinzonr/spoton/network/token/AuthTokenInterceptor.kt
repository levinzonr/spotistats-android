package cz.levinzonr.spoton.network.token

import cz.levinzonr.spoton.repositories.AuthTokenRepository
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
        tokenRepository.accessToken?.let {
            request.header("Authorization", "Bearer $it") }
        return chain.proceed(request.build())
    }
}