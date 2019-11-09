package cz.levinzonr.spoton.presentation.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import cz.levinzonr.spoton.presentation.util.EventObserver
import cz.levinzonr.spoton.presentation.util.SingleEvent
import cz.levinzonr.spoton.presentation.util.ViewError
import cz.levinzonr.spoton.presentation.util.ViewErrorController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

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


fun Throwable.toErrorEvent() : SingleEvent<ViewError> = SingleEvent(ViewErrorController.mapThrowable(this)).also {
    Timber.e(this)
}

fun<T> ViewModel.flowOnIO(block: suspend FlowCollector<T>.() -> Unit) : Flow<T> {
    return flow(block).flowOn(Dispatchers.IO)
}

fun<T> ViewModel.flowOnMain(block: suspend FlowCollector<T>.() -> Unit) : Flow<T> {
    return flow(block).flowOn(Dispatchers.Main)
}

