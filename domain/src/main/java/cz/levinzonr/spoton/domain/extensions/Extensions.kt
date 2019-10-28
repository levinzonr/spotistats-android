package cz.levinzonr.spoton.domain.extensions

import cz.levinzonr.spoton.domain.interactors.BaseAsyncInteractor
import cz.levinzonr.spoton.domain.interactors.CompleteResult
import cz.levinzonr.spoton.domain.interactors.Fail
import cz.levinzonr.spoton.domain.interactors.Success

inline fun <T> T.guard(block: T.() -> Unit): T {
    if (this == null) block(); return this
}

inline fun <T> BaseAsyncInteractor<CompleteResult<T>>.safeInteractorCall(block: () -> T): CompleteResult<T> {
    return try {
        Success(block.invoke())
    } catch (t: Throwable) {
        Fail(t)
    }
}

val String.spotifyTrackUri
    get() = "spotify:track:$this"