package cz.levinzonr.spotistats.presentation.screens.onboarding.login

import android.content.Intent
import androidx.fragment.app.Fragment
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import cz.levinzonr.spotistats.domain.interactors.LoginInteractor
import cz.levinzonr.spotistats.models.SpotifyCredentials
import cz.levinzonr.spotistats.presentation.base.BaseViewModel
import cz.levinzonr.spotistats.presentation.extensions.isError
import cz.levinzonr.spotistats.presentation.extensions.isSuccess
import cz.levinzonr.spotistats.presentation.navigation.Route
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.qualifier.named
import timber.log.Timber

class LoginViewModel(
        private val credentials: SpotifyCredentials,
        private val loginInteractor: LoginInteractor
) : BaseViewModel<Action, Change, State>(), KoinComponent {

    private val REQUEST_CODE = 12321
    override val initialState: State = State(false)


    override val reducer: suspend (state: State, change: Change) -> State = { state, change ->
        when(change) {
            is Change.LoginFailed -> state.copy(isLoading = false)
            is Change.LoginStarted -> state.copy(isLoading = true)
            is Change.LoginSuccess -> state.copy(isLoading = false).also {
                navigateTo(Route.Main)
            }
        }

    }

    init {
        startActionsObserver()
    }

    override fun emitAction(action: Action): Flow<Change> {
       return when(action) {
           is Action.LoginClicked -> bindLoginAction(action.fragment)
           is Action.LoginResult -> bindHandleLoginResult(action.requestCode, action.resultCode, action.data)
       }
    }

    private fun bindLoginAction(fragment: Fragment) : Flow<Change> = flow {
        emit(Change.LoginStarted)
        val builder = AuthenticationRequest.Builder(credentials.clientId, AuthenticationResponse.Type.CODE, credentials.redirectUri)
        builder.setScopes(arrayOf("streaming, user-top-read", "playlist-modify-public"))
        val request = builder.build()
        val intent = AuthenticationClient.createLoginActivityIntent(fragment.requireActivity(), request)
        fragment.startActivityForResult(intent, REQUEST_CODE)
    }

    private fun bindHandleLoginResult(requectCode: Int, resultCode: Int, data: Intent?) : Flow<Change> = flow {
        if (requectCode == REQUEST_CODE) {
            val response = AuthenticationClient.getResponse(resultCode, data)
            when(response.type) {
                AuthenticationResponse.Type.CODE -> {
                    loginInteractor.input = LoginInteractor.Input(response.code)
                    loginInteractor.invoke()
                            .isSuccess { emit(Change.LoginSuccess) }
                            .isError { Timber.d("Failed $it"); emit(Change.LoginFailed) }
                }
               else -> {
                   emit(Change.LoginFailed)
               }
            }
        }
    }
}