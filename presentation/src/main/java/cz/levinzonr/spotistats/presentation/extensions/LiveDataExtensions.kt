package cz.levinzonr.spotistats.presentation.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import cz.levinzonr.spotistats.presentation.util.EventObserver
import cz.levinzonr.spotistats.presentation.util.SingleEvent

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
