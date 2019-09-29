package cz.levinzonr.spotistats.presentation.ui.sample

import androidx.lifecycle.viewModelScope
import cz.levinzonr.spotistats.domain.interactors.*
import cz.levinzonr.spotistats.models.Post
import cz.levinzonr.spotistats.presentation.extensions.asResult
import cz.levinzonr.spotistats.presentation.ui.base.BaseViewModel
import cz.levinzonr.spotistats.presentation.util.SingleEvent
import cz.levinzonr.spotistats.presentation.util.ViewErrorController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SampleViewModel @Inject constructor(
    postsInteractor: PostsInteractor
) : BaseViewModel<SampleViewState>() {

    override val initState: SampleViewState = SampleViewState()

    private val resultInteractor = postsInteractor.asResult()

    fun fetchPosts() = viewModelScope.launch(Dispatchers.Main) {
        state = mapResult(Loading())
        val result = withContext(Dispatchers.IO) { resultInteractor.invoke() }
        state = mapResult(result)
    }

    private fun mapResult(result: InteractorResult<List<Post>>): SampleViewState {
        return when (result) {
            is Success -> state.copy(posts = result.data, isLoading = false)
            is Loading -> state.copy(isLoading = true)
            is Fail -> state.copy(
                viewError = SingleEvent(ViewErrorController.mapThrowable(result.throwable)),
                isLoading = false
            )
            else -> SampleViewState()
        }
    }
}