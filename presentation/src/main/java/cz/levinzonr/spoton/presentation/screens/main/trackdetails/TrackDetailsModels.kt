package cz.levinzonr.spoton.presentation.screens.main.trackdetails

import com.ww.roxie.BaseAction
import com.ww.roxie.BaseChange
import com.ww.roxie.BaseState
import cz.levinzonr.spoton.domain.models.RemotePlayerState
import cz.levinzonr.spoton.models.RecommendedTracks
import cz.levinzonr.spoton.models.TrackFeaturesResponse
import cz.levinzonr.spoton.models.TrackResponse
import cz.levinzonr.spoton.presentation.navigation.Route
import cz.levinzonr.spoton.presentation.util.SingleEvent
import cz.levinzonr.spoton.presentation.util.Source


sealed class Change : BaseChange {
    data class TrackLoaded(val track: TrackResponse) : Change()
    data class FeaturesLoaded(val featuresResponse: TrackFeaturesResponse) : Change()
    data class RecommendedLoaded(val recommendedTracks: RecommendedTracks) : Change()

    data class TrackLoadingError(val throwable: Throwable) : Change()
    data class FeaturesLoadingError(val throwable: Throwable) : Change()
    data class RecommendedLoadingError(val throwable: Throwable) : Change()


    data class RemoteStateLoading(val remotePlayerState: RemotePlayerState) : Change()
    data class RemoteStateReady(val remotePlayerState: RemotePlayerState) : Change()
    data class RemoteStateError(val remotePlayerState: RemotePlayerState, val error: Throwable) : Change()

    data class PlayerActionSuccess(val message: String) : Change()
    data class PlayerActionError(val error: String) : Change()

    data class Navigation(val route: Route) : Change()

    object TrackLoading : Change()
    object FeaturesLoading : Change()
    object RecommendedLoading : Change()
    data class TrackPlaying(val isPlaying: Boolean) : Change()

}

sealed class Action : BaseAction {
    data class RemoteStateUpdated(val remotePlayerState: RemotePlayerState) : Action()
    data class PlayTrackClicked(val trackId: String) : Action()
    data class QueueTrackClicked(val trackId: String) : Action()
    data class AddToPlaylistClicked(val trackId: String) : Action()

    data class LoadTrack(val trackId: String) : Action()
    data class LoadFeatures(val trackId: String) : Action()
    data class LoadRecommended(val track: TrackResponse) : Action()

    data class RecommendedTrackClicked(val trackResponse: TrackResponse) : Action()

}

data class State(
        val isPlaying: Boolean = false,
        val toast: SingleEvent<String>? = null,
        val remotePlayerReady: Boolean = false,
        val featuresSource: Source<TrackFeaturesResponse> = Source(),
        val trackSource: Source<TrackResponse> = Source(),
        val recommendedSource: Source<RecommendedTracks> = Source()
) : BaseState

