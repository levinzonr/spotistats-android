package cz.levinzonr.spotistats.presentation.screens.main.trackdetails

import cz.levinzonr.spotistats.domain.interactors.GetRecommendedTracks
import cz.levinzonr.spotistats.domain.interactors.GetTrackDetailsInteractor
import cz.levinzonr.spotistats.domain.interactors.GetTrackFeatures
import cz.levinzonr.spotistats.domain.managers.SpotifyRemoteManager
import cz.levinzonr.spotistats.domain.models.RemotePlayerState
import cz.levinzonr.spotistats.presentation.base.BaseViewModel
import cz.levinzonr.spotistats.presentation.extensions.flowOnIO
import cz.levinzonr.spotistats.presentation.extensions.isError
import cz.levinzonr.spotistats.presentation.extensions.isSuccess
import cz.levinzonr.spotistats.presentation.util.error
import cz.levinzonr.spotistats.presentation.util.loaded
import cz.levinzonr.spotistats.presentation.util.loading
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TrackDetailsViewModel(
        private val trackId: String,
        private val getRecommendedTracks: GetRecommendedTracks,
        private val spotifyRemoteManager: SpotifyRemoteManager,
        private val getTrackFeatures: GetTrackFeatures,
        private val getTrackDetailsInteractor: GetTrackDetailsInteractor
) : BaseViewModel<Action, Change, State>() {

    override val initialState: State = State()


    override val reducer: suspend (state: State, change: Change) -> State = { state, change ->
        when (change) {
            is Change.FeaturesLoaded -> state.copy(featuresSource = state.featuresSource.loaded(change.featuresResponse))
            is Change.TrackLoaded -> state.copy(trackSource = state.trackSource.loaded(change.track))
            is Change.RecommendedLoaded -> state.copy(recommendedSource = state.recommendedSource.loaded(change.recommendedTracks))

            is Change.RecommendedLoadingError -> state.copy(recommendedSource = state.recommendedSource.error(change.throwable))
            is Change.TrackLoadingError -> state.copy(recommendedSource = state.recommendedSource.error(change.throwable))
            is Change.FeaturesLoadingError -> state.copy(recommendedSource = state.recommendedSource.error(change.throwable))

            is Change.RecommendedLoading -> state.copy(recommendedSource = state.recommendedSource.loading())
            is Change.FeaturesLoading -> state.copy(recommendedSource = state.recommendedSource.loading())
            is Change.TrackLoading -> state.copy(recommendedSource = state.recommendedSource.loading())

            is Change.RemoteStateReady -> state.copy(remotePlayerReady = true)
            is Change.RemoteStateLoading -> state.copy(remotePlayerReady = false)
            is Change.RemoteStateError -> state.copy(remotePlayerReady = false)
        }
    }

    init {
        startActionsObserver()
        addStateSource(spotifyRemoteManager.stateLiveData) {
            dispatch(Action.RemoteStateUpdated(it))
        }
        dispatch(Action.LoadFeatures(trackId))
        dispatch(Action.LoadTrack(trackId))
    }

    override fun emitAction(action: Action): Flow<Change> {
        return when (action) {
            is Action.LoadFeatures -> bindLoadFeaturesAction(action.trackId)
            is Action.LoadRecommended -> bindLoadRecommendedAction()
            is Action.LoadTrack -> bindLoadTrackAction(action.trackId)
            is Action.RemoteStateUpdated -> bindRemoteStateUpdate(action.remotePlayerState)
            is Action.PlayTrackClicked -> bindPlayTrackAction(action.trackId)
            is Action.QueueTrackClicked -> bindQueueTrackAction(action.trackId)

        }
    }

    private fun bindLoadFeaturesAction(id: String): Flow<Change> = flowOnIO {
        emit(Change.FeaturesLoading)
        getTrackFeatures.input = GetTrackFeatures.Input(id)
        getTrackFeatures()
                .isSuccess { emit(Change.FeaturesLoaded(it)) }
                .isError { emit(Change.FeaturesLoadingError(it)) }

    }

    private fun bindPlayTrackAction(id: String) : Flow<Change> = flowOnIO {
        spotifyRemoteManager.play("spotify:track:$id")
    }

    private fun bindQueueTrackAction(id: String) : Flow<Change> = flowOnIO {
        spotifyRemoteManager.addToQueue("spotify:track:$id")
    }

    private fun bindLoadTrackAction(id: String): Flow<Change> = flowOnIO {
        emit(Change.TrackLoading)
        getTrackDetailsInteractor.input = GetTrackDetailsInteractor.Input(id)
        getTrackDetailsInteractor()
                .isSuccess { emit(Change.TrackLoaded(it)) }
                .isError { emit(Change.TrackLoadingError(it)) }

    }

    private fun bindLoadRecommendedAction(): Flow<Change> = flowOnIO {

    }


    private fun bindRemoteStateUpdate(remotePlayerState: RemotePlayerState) : Flow<Change> = flow {
        when(remotePlayerState) {
            is RemotePlayerState.Ready -> emit(Change.RemoteStateReady(remotePlayerState))
            is RemotePlayerState.Error -> emit(Change.RemoteStateError(remotePlayerState))
            is RemotePlayerState.Initilizing -> emit(Change.RemoteStateLoading(remotePlayerState))
        }
    }
}