package cz.levinzonr.spoton.domain.interactors

import com.google.android.gms.tasks.Tasks
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import cz.levinzonr.spoton.domain.extensions.safeInteractorCall

class RefreshAppConfigInteractor(
        private val remoteConfig: FirebaseRemoteConfig
) : BaseAsyncInteractor<CompleteResult<Unit>> {

    override suspend fun invoke(): CompleteResult<Unit> = safeInteractorCall {
        Tasks.await(remoteConfig.fetchAndActivate())
        println("FIrebase: Done")
    }
}