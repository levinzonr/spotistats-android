package cz.levinzonr.spoton.domain.interactors

import cz.levinzonr.spoton.domain.extensions.safeInteractorCall
import cz.levinzonr.spoton.repositories.AuthTokenRepository
import cz.levinzonr.spoton.repositories.KeychainRepository

class LoginInteractor(
        private val keychainRepository: KeychainRepository,
        private val authTokenRepository: AuthTokenRepository) : BaseAsyncInteractor<CompleteResult<Boolean>> {

    data class Input(val code: String)

    var input: Input? = null

    override suspend fun invoke() = safeInteractorCall {
        val code = requireNotNull(input).code
        keychainRepository.obtainAccessToken(code)
        true
    }
}