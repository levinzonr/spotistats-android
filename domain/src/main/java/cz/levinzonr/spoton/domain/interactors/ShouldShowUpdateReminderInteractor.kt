package cz.levinzonr.spoton.domain.interactors

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import cz.levinzonr.spoton.domain.BuildConfig
import cz.levinzonr.spoton.domain.extensions.safeInteractorCall
import cz.levinzonr.spoton.domain.managers.AppConfig
import cz.levinzonr.spoton.repositories.PrefManager

class ShouldShowUpdateReminderInteractor(
        private val remoteConfig: FirebaseRemoteConfig,
        private val appConfig: AppConfig,
        private val prefManager: PrefManager
) : BaseAsyncInteractor<CompleteResult<Boolean>> {
    override suspend fun invoke(): CompleteResult<Boolean> = safeInteractorCall {
        val interval = if (BuildConfig.DEBUG) INTERVAL_DEBBUG else INTERVAL
        val newVersion = remoteConfig.getLong("latest_app_version")
        if (newVersion > appConfig.versionCode) {
            val last = prefManager.getLong(PREF_LAST_SHOWN, 0)
            val timePassed = System.currentTimeMillis() - last
            if (timePassed >= interval) {
                prefManager.setLong(PREF_LAST_SHOWN, System.currentTimeMillis())
                true
            } else false
        } else {
            false
        }
    }


    companion object {
        private const val PREF_LAST_SHOWN = "pref_last_time_shown"
        private const val INTERVAL = 18_000_000
        private const val INTERVAL_DEBBUG = 10_000

    }
}