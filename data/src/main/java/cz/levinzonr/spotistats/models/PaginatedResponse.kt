package cz.levinzonr.spotistats.models

data class PaginatedResponse<T>(
        val href: String,
        val items: List<T>,
        val limit: Int,
        val next: String,
        val offset: Int,
        val previous: Any,
        val total: Int
)