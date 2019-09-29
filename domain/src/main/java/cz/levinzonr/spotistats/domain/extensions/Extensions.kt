package cz.levinzonr.spotistats.domain.extensions

inline fun <T> T.guard(block: T.() -> Unit): T {
    if (this == null) block(); return this
}