package cz.levinzonr.spoton.domain.interactors

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import cz.levinzonr.spoton.domain.extensions.safeInteractorCall
import cz.levinzonr.spoton.domain.managers.AppConfig

class ShouldShowUpdateReminderInteractor(
        private val remoteConfig: FirebaseRemoteConfig,
        private val appConfig: AppConfig
) : BaseAsyncInteractor<CompleteResult<Boolean>> {
    override suspend fun invoke(): CompleteResult<Boolean> = safeInteractorCall {
        val newVersion = remoteConfig.getLong("latest_app_version")
        newVersion > appConfig.versionCode
    }
}