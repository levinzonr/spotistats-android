package cz.levinzonr.spotistats.domain.interactors

import cz.levinzonr.spotistats.domain.extensions.safeInteractorCall
import cz.levinzonr.spotistats.models.UserResponse
import cz.levinzonr.spotistats.repositories.UserRepository

class GetUserProfileInteractor(private val userRepository: UserRepository) : BaseAsyncInteractor<CompleteResult<UserResponse>> {

    override suspend fun invoke(): CompleteResult<UserResponse> = safeInteractorCall {
        userRepository.getCurrentUserProfile()
    }
}