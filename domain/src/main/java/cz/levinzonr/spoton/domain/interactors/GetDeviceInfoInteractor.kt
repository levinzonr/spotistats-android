package cz.levinzonr.spoton.domain.interactors

import android.os.Build
import cz.levinzonr.spoton.domain.BuildConfig
import cz.levinzonr.spoton.domain.extensions.safeInteractorCall
import cz.levinzonr.spoton.domain.managers.AppConfig
import cz.levinzonr.spoton.models.DeviceInfo

class GetDeviceInfoInteractor(private val appConfig: AppConfig) : BaseAsyncInteractor<CompleteResult<DeviceInfo>> {
    override suspend fun invoke(): CompleteResult<DeviceInfo> {
        val buildVersion = appConfig.versionName
        val buildNumber = appConfig.versionCode
        val device = "${Build.MANUFACTURER} ${Build.MODEL} (${Build.DEVICE})"
        val androidVersion = "${Build.VERSION.RELEASE} (${Build.VERSION.SDK_INT})"
        return safeInteractorCall {
            DeviceInfo(buildVersion, buildNumber.toString(), device, androidVersion)
        }
    }
}