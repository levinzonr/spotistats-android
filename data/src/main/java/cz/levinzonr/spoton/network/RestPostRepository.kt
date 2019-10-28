package cz.levinzonr.spoton.network

import cz.levinzonr.spoton.models.Post
import cz.levinzonr.spoton.repositories.PostRepository
import cz.levinzonr.spoton.repositories.RepositoryException

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