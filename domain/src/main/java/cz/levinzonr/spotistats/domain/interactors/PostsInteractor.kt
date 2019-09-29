package cz.levinzonr.spotistats.domain.interactors

import cz.levinzonr.spotistats.models.Post
import cz.levinzonr.spotistats.repositories.PostRepository
import javax.inject.Inject

class PostsInteractor @Inject constructor(
    private val postRepository: PostRepository
) : BaseAsyncInteractor<List<Post>> {

    override suspend fun invoke(): List<Post> {
        return postRepository.getPosts(true)
    }
}