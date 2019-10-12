package cz.levinzonr.spotistats.domain.interactors

import cz.levinzonr.spotistats.domain.extensions.safeInteractorCall
import cz.levinzonr.spotistats.repositories.AuthTokenRepository
import cz.levinzonr.spotistats.repositories.KeychainRepository

class LoginInteractor(
        private val keychainRepository: KeychainRepository,
        private val authTokenRepository: AuthTokenRepository) : BaseAsyncInteractor<CompleteResult<Boolean>> {

    data class Input(val code: String)

    var input: Input? = null

    override suspend fun invoke() = safeInteractorCall {
        val code = requireNotNull(input).code
        val accessToken = keychainRepository.obtainAccessToken(code)
        authTokenRepository.set(accessToken)
        true
    }
}