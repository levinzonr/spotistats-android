package cz.levinzonr.spotistats.network

import cz.levinzonr.spotistats.models.Post
import cz.levinzonr.spotistats.repositories.PostRepository
import cz.levinzonr.spotistats.repositories.RepositoryException

class RestPostRepository(private val api: Api) : PostRepository {
    @Throws(RepositoryException::class)
    override suspend fun getPosts(cached: Boolean): List<Post> {
        val response = api.getPosts()
        if (response.isSuccessful) {
            return response.body()
                ?: throw(RepositoryException(
                    response.code(),
                    response.errorBody()?.string(),
                    response.message()
                ))
        } else {
            throw(RepositoryException(
                    response.code(),
                    response.errorBody()?.string(),
                    response.message()
            ))
        }
    }
}