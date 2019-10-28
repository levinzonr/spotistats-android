package cz.levinzonr.spoton.domain.interactors

import cz.levinzonr.spoton.domain.extensions.safeInteractorCall
import cz.levinzonr.spoton.domain.models.UserProfile
import cz.levinzonr.spoton.models.UserResponse
import cz.levinzonr.spoton.network.Api
import cz.levinzonr.spoton.repositories.UserRepository

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