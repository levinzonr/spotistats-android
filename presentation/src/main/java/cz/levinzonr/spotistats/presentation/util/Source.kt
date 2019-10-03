package cz.levinzonr.spotistats.presentation.util

import cz.levinzonr.spotistats.presentation.extensions.toErrorEvent

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
