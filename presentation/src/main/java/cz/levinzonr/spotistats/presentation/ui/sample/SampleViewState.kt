package cz.levinzonr.spotistats.presentation.ui.sample

import cz.levinzonr.spotistats.models.Post
import cz.levinzonr.spotistats.presentation.util.SingleEvent
import cz.levinzonr.spotistats.presentation.util.ViewError

data class SampleViewState(
    val posts: List<Post> = emptyList(),
    val viewError: SingleEvent<ViewError>? = null,
    val isLoading: Boolean = false
)