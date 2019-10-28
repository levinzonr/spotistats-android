package cz.levinzonr.spoton.presentation.util

import cz.levinzonr.spoton.presentation.extensions.toErrorEvent

data class Source<T>(
        val data: T? = null,
        val isLoading: Boolean = false,
        val error: SingleEvent<ViewError>? = null
) {
    override fun toString(): String {
        return when {
            data != null -> "Data: $data"
            isLoading -> "Loading..."
            error != null -> "Error: ${error.peek().message}"
            else -> "Defailt"
        }
    }
}

sealed class DataState<out T>  {
    object LoadingStarted : DataState<Nothing>()
    data class LoadingFinished<T>(val data: T) : DataState<T>()
    data class LoadingError(val throwable: Throwable) : DataState<Throwable>()
}

fun <T> Source<T>.loaded(data: T): Source<T> = copy(
        data = data,
        isLoading = false,
        error = null
)

fun <T> Source<T>.loading(): Source<T> = copy(
        data = null,
        isLoading = true,
        error = null
)
fun <T> Source<T>.error(error: Throwable): Source<T> = copy(
        data = null,
        isLoading = false,
        error = error.toErrorEvent()
)
