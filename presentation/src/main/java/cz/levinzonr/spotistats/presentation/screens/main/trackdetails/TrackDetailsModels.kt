package cz.levinzonr.spotistats.presentation.screens.main.trackdetails

import com.ww.roxie.BaseAction
import com.ww.roxie.BaseChange
import com.ww.roxie.BaseState
import cz.levinzonr.spotistats.models.RecommendedTracks
import cz.levinzonr.spotistats.models.TrackFeaturesResponse
import cz.levinzonr.spotistats.models.TrackResponse
import cz.levinzonr.spotistats.presentation.util.Source


sealed class Change : BaseChange {
    data class TrackLoaded(val track: TrackResponse) : Change()
    data class FeaturesLoaded(val featuresResponse: TrackFeaturesResponse) : Change()
    data class RecommendedLoaded(val recommendedTracks: RecommendedTracks) : Change()

    data class TrackLoadingError(val throwable: Throwable) : Change()
    data class FeaturesLoadingError(val throwable: Throwable) : Change()
    data class RecommendedLoadingError(val throwable: Throwable) : Change()

    object TrackLoading : Change()
    object FeaturesLoading : Change()
    object RecommendedLoading : Change()

}

sealed class Action : BaseAction {
    data class LoadTrack(val trackId: String) : Action()
    data class LoadFeatures(val trackId: String) : Action()
    data class LoadRecommended(val track: TrackResponse, val genres: List<String>) : Action()

}

data class State(
      val featuresSource: Source<TrackFeaturesResponse> = Source(),
      val trackSource: Source<TrackResponse> = Source(),
      val recommendedSource: Source<RecommendedTracks> = Source()
) : BaseState

