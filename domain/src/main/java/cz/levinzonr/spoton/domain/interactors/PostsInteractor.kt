package cz.levinzonr.spoton.domain.interactors

import cz.levinzonr.spoton.models.Post
import cz.levinzonr.spoton.repositories.PostRepository

class PostsInteractor(
    private val postRepository: PostRepository
) : BaseAsyncInteractor<List<Post>> {

    override suspend fun invoke(): List<Post> {
        return postRepository.getPosts(true)
    }
}