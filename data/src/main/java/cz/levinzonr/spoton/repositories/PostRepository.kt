package cz.levinzonr.spoton.repositories

import cz.levinzonr.spoton.models.Post

interface PostRepository {
    suspend fun getPosts(cached: Boolean = false): List<Post>
}