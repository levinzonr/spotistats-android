package cz.levinzonr.spoton

import cz.levinzonr.spoton.domain.managers.AppConfig

class AppConfigImpl: AppConfig {
    override val versionName: String = BuildConfig.VERSION_NAME
    override val versionCode: Int = BuildConfig.VERSION_CODE
}
