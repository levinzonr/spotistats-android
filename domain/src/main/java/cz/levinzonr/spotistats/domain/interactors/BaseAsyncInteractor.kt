package cz.levinzonr.spotistats.domain.interactors

interface BaseAsyncInteractor<O> {
    suspend operator fun invoke(): O
}