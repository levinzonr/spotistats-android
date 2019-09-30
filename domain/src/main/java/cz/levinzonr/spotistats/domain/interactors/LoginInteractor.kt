package cz.levinzonr.spotistats.domain.interactors

import cz.levinzonr.spotistats.repositories.AuthTokenRepository

class LoginInteractor(private val authTokenRepository: AuthTokenRepository) : BaseAsyncInteractor<Unit> {

    data class Input(val token: String)

    var input: Input? = null

    override suspend fun invoke() {
        val token = requireNotNull(input).token
        authTokenRepository.set(token)
    }
}