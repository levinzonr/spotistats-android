package cz.levinzonr.spotistats.presentation.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import cz.levinzonr.spotistats.presentation.util.EventObserver
import cz.levinzonr.spotistats.presentation.util.SingleEvent
import cz.levinzonr.spotistats.presentation.util.ViewError
import cz.levinzonr.spotistats.presentation.util.ViewErrorController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

inline fun <T> LiveData<T>.observe(
    lifecycleOwner: LifecycleOwner,
    crossinline observer: (T?) -> Unit
) {
    this.observe(lifecycleOwner, Observer {
        observer(it)
    })
}

inline fun <T> LiveData<T>.observeNonNull(
    lifecycleOwner: LifecycleOwner,
    crossinline observer: (T) -> Unit
) {
    this.observe(lifecycleOwner, Observer {
        it?.let(observer)
    })
}

inline fun <E, T : SingleEvent<E>> LiveData<T>.observeEvent(
    lifecycleOwner: LifecycleOwner,
    crossinline observer: (E) -> Unit
) {
    this.observe(lifecycleOwner, EventObserver {
        observer(it)
    })
}


fun Throwable.toErrorEvent() : SingleEvent<ViewError> = SingleEvent(ViewErrorController.mapThrowable(this))


fun<T> ViewModel.flowOnIO(block: suspend FlowCollector<T>.() -> Unit) : Flow<T> {
    return flow(block).flowOn(Dispatchers.IO)
}

