package cz.levinzonr.spoton.presentation.screens.main.trackdetails

import cz.levinzonr.spoton.domain.extensions.spotifyTrackUri
import cz.levinzonr.spoton.domain.interactors.GetRecommendedTracks
import cz.levinzonr.spoton.domain.interactors.GetTrackDetailsInteractor
import cz.levinzonr.spoton.domain.interactors.GetTrackFeatures
import cz.levinzonr.spoton.domain.managers.SpotifyRemoteManager
import cz.levinzonr.spoton.domain.models.PlayerActionResult
import cz.levinzonr.spoton.domain.models.RemotePlayerState
import cz.levinzonr.spoton.models.TrackResponse
import cz.levinzonr.spoton.presentation.base.BaseViewModel
import cz.levinzonr.spoton.presentation.extensions.flowOnIO
import cz.levinzonr.spoton.presentation.extensions.isError
import cz.levinzonr.spoton.presentation.extensions.isSuccess
import cz.levinzonr.spoton.presentation.navigation.Route
import cz.levinzonr.spoton.presentation.util.SingleEvent
import cz.levinzonr.spoton.presentation.util.error
import cz.levinzonr.spoton.presentation.util.loaded
import cz.levinzonr.spoton.presentation.util.loading
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

            is Change.PlayerActionError -> state.copy(toast = SingleEvent(change.error))
            is Change.PlayerActionSuccess -> state.copy(toast = SingleEvent(change.message))


            is Change.TrackPlaying -> state.copy(isPlaying = change.isPlaying)

            is Change.RemoteStateReady -> state.copy(remotePlayerReady = true)
            is Change.RemoteStateLoading -> state.copy(remotePlayerReady = false)

            is Change.RemoteStateError -> state.copy(
                    remotePlayerReady = false,
                    toast = SingleEvent(change.error.localizedMessage))

            is Change.Navigation -> state.copy().also { navigateTo(change.route) }
        }
    }

    init {
        startActionsObserver()
        spotifyRemoteManager.connect()
        addStateSource(spotifyRemoteManager.stateLiveData) {
            dispatch(Action.RemoteStateUpdated(it))
        }
        dispatch(Action.LoadFeatures(trackId))
        dispatch(Action.LoadTrack(trackId))
    }

    override fun emitAction(action: Action): Flow<Change> {
        return when (action) {
            is Action.LoadFeatures -> bindLoadFeaturesAction(action.trackId)
            is Action.LoadRecommended -> bindLoadRecommendedAction(action.track)
            is Action.LoadTrack -> bindLoadTrackAction(action.trackId)
            is Action.RemoteStateUpdated -> bindRemoteStateUpdate(action.remotePlayerState)
            is Action.PlayTrackClicked -> bindPlayTrackAction(action.trackId)
            is Action.QueueTrackClicked -> bindQueueTrackAction(action.trackId)
            is Action.AddToPlaylistClicked -> bindAddToPlaylistAction(action.trackId)
            is Action.RecommendedTrackClicked -> bindRecommendedTrackClicked(action.trackResponse)
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

        val isPlaying = currentState.isPlaying
        when(val result = if (isPlaying) spotifyRemoteManager.pause() else spotifyRemoteManager.play(id.spotifyTrackUri)) {
            is PlayerActionResult.Success -> emit(Change.PlayerActionSuccess("Track playback started"))
            is PlayerActionResult.Error -> emit(Change.PlayerActionError("Error playing track (${result.message}"))
        }    }

    private fun bindQueueTrackAction(id: String) : Flow<Change> = flowOnIO {
        when(val result = spotifyRemoteManager.addToQueue(id.spotifyTrackUri)) {
            is PlayerActionResult.Success -> emit(Change.PlayerActionSuccess("Added to queue"))
            is PlayerActionResult.Error -> emit(Change.PlayerActionError("Error adding to gueue (${result.message}"))
        }
    }

    private fun bindLoadTrackAction(id: String): Flow<Change> = flowOnIO {
        emit(Change.TrackLoading)
        getTrackDetailsInteractor.input = GetTrackDetailsInteractor.Input(id)
        getTrackDetailsInteractor()
                .isSuccess {
                    emit(Change.TrackLoaded(it))
                    dispatch(Action.LoadRecommended(it))
                }
                .isError { emit(Change.TrackLoadingError(it)) }

    }

    private fun bindLoadRecommendedAction(trackResponse: TrackResponse): Flow<Change> = flowOnIO {
        emit(Change.RecommendedLoading)
        getRecommendedTracks.input = GetRecommendedTracks.Input(listOf(trackResponse),
                trackResponse.artists, listOf())

        getRecommendedTracks.invoke()
                .isSuccess { emit(Change.RecommendedLoaded(it)) }
                .isError { emit(Change.RecommendedLoadingError(it)) }
    }

    private fun bindAddToPlaylistAction(trackId: String) : Flow<Change> = flow {
        val route = Route.Destination(TrackDetailsFragmentDirections.actionTrackDetailsFragmentToPlaylistsDialogFragment(trackId))
        emit(Change.Navigation(route))
    }

    private fun bindRecommendedTrackClicked(trackResponse: TrackResponse) = flow {
        val route = Route.Destination(TrackDetailsFragmentDirections.actionTrackDetailsFragmentSelf(trackResponse.id))
        emit(Change.Navigation(route))
    }

    private fun bindRemoteStateUpdate(remotePlayerState: RemotePlayerState) : Flow<Change> = flow {
        when(remotePlayerState) {
            is RemotePlayerState.Ready -> {
                val currentTrack = remotePlayerState.state.track?.uri == trackId.spotifyTrackUri
                emit(Change.TrackPlaying(currentTrack && !remotePlayerState.state.isPaused))
                emit(Change.RemoteStateReady(remotePlayerState))
            }
            is RemotePlayerState.Error -> emit(Change.RemoteStateError(remotePlayerState, remotePlayerState.throwable ?: Exception("Null")))
            is RemotePlayerState.Initilizing -> emit(Change.RemoteStateLoading(remotePlayerState))
        }
    }

    override fun onCleared() {
        super.onCleared()
        spotifyRemoteManager.disconnect()
    }
}