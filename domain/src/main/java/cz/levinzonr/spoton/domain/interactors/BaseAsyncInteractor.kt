package cz.levinzonr.spoton.domain.interactors

interface BaseAsyncInteractor<O> {
    suspend operator fun invoke(): O
}