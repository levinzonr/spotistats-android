package cz.levinzonr.spotistats.presentation.ui.onboarding.login

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.spotify.sdk.android.authentication.AuthenticationClient
import com.spotify.sdk.android.authentication.AuthenticationRequest
import com.spotify.sdk.android.authentication.AuthenticationResponse
import cz.levinzonr.spotistats.domain.managers.UserManager
import cz.levinzonr.spotistats.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LoginViewModel(
        private val userManager: UserManager,
        private val clientId: String
) : BaseViewModel<Action, Change, State>() {

    private val REQUEST_CODE = 12321

    override val initialState: State = State(false)



    override val reducer: suspend (state: State, change: Change) -> State = { state, change ->
        when(change) {
            is Change.LoginFailed -> state.copy(isLoading = false)
            is Change.LoginStarted -> state.copy(isLoading = true)
            is Change.LoginSuccess -> state.copy(isLoading = false)
        }
    }

    override fun emitAction(action: Action): Flow<Change> {
       return when(action) {
           is Action.LoginClicked -> bindLoginAction(action.fragment)
           is Action.LoginResult -> bindHandleLoginResult(action.requestCode, action.resultCode, action.data)
       }
    }

    private fun bindLoginAction(fragment: Fragment) : Flow<Change> = flow {
        emit(Change.LoginStarted)
        val builder = AuthenticationRequest.Builder(clientId, AuthenticationResponse.Type.TOKEN, "yourcustomprotocol://callback")
        builder.setScopes(arrayOf("streaming"))
        val request = builder.build()
        val intent = AuthenticationClient.createLoginActivityIntent(fragment.requireActivity(), request)
        fragment.startActivityForResult(intent, REQUEST_CODE)
    }

    private fun bindHandleLoginResult(requectCode: Int, resultCode: Int, data: Intent?) : Flow<Change> = flow {
        if (requectCode == REQUEST_CODE) {
            val response = AuthenticationClient.getResponse(resultCode, data)
            when(response.type) {
                AuthenticationResponse.Type.TOKEN -> {
                    emit(Change.LoginSuccess)
                }
               else -> {
                   emit(Change.LoginFailed)
               }
            }
        }
    }
}