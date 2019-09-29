package cz.levinzonr.spotistats.repositories

import cz.levinzonr.spotistats.models.Post

interface PostRepository {
    suspend fun getPosts(cached: Boolean = false): List<Post>
}