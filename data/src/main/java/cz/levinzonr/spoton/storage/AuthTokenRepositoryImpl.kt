package cz.levinzonr.spoton.storage

import com.google.gson.Gson
import cz.levinzonr.spoton.models.AccessToken
import cz.levinzonr.spoton.repositories.AuthTokenRepository
import cz.levinzonr.spoton.repositories.PrefManager

class AuthTokenRepositoryImpl(
        private val gson: Gson,
        private val prefManager: PrefManager) : AuthTokenRepository {


    override var accessToken: String?
        get() = prefManager.getString(PREF_TOKEN, null)
        set(value) {
            if (!value.isNullOrBlank())
                prefManager.setString(PREF_TOKEN, value)
        }


    override var refreshToken: String?
        get() = prefManager.getString(PREF_REFRESH_TOKEN, null)
        set(value) {
            if (!value.isNullOrBlank()) {
                prefManager.setString(PREF_REFRESH_TOKEN, value)
            }
        }

    override fun set(accessToken: AccessToken) {
        this.accessToken = accessToken.access_token
        this.refreshToken = accessToken.refresh_token
    }

    override fun clear() {
        prefManager.remove(PREF_TOKEN)
    }

    companion object {
        private const val PREF_REFRESH_TOKEN = "pref::refresh_token"
        private const val PREF_TOKEN = "pref::access_token"
    }
}