package cz.levinzonr.spotistats.domain.interactors

import cz.levinzonr.spotistats.domain.extensions.safeInteractorCall
import cz.levinzonr.spotistats.domain.models.UserProfile
import cz.levinzonr.spotistats.models.UserResponse
import cz.levinzonr.spotistats.network.Api
import cz.levinzonr.spotistats.repositories.UserRepository

class GetUserProfileInteractor(
        private val api: Api,
        private val userRepository: UserRepository) : BaseAsyncInteractor<CompleteResult<UserProfile>> {

    override suspend fun invoke(): CompleteResult<UserProfile> = safeInteractorCall {
        val profile = userRepository.getCurrentUserProfile()
        val playlistCount = api.getUserPlaylists().total
        return@safeInteractorCall UserProfile(
                displayName = profile.display_name,
                imageUrl = profile.images.first().url,
                playlistCount = playlistCount,
                followersCount = profile.followers.total
                )
    }
}