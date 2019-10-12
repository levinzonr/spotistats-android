package cz.levinzonr.spotistats.storage

import com.google.gson.Gson
import cz.levinzonr.spotistats.models.AccessToken
import cz.levinzonr.spotistats.repositories.AuthTokenRepository
import cz.levinzonr.spotistats.repositories.PrefManager

class AuthTokenRepositoryImpl(
        private val gson: Gson,
        private val prefManager: PrefManager) : AuthTokenRepository {


    override fun get(): AccessToken? {
        return gson.fromJson(prefManager.getString(PREF_TOKEN, null), AccessToken::class.java)
    }

    override fun set(accessToken: AccessToken) {
        val json = gson.toJson(accessToken)
        prefManager.setString(PREF_TOKEN, json)
    }

    override fun clear() {
        prefManager.remove(PREF_TOKEN)
    }

    companion object {
        private const val PREF_TOKEN = "pref::access_token"
    }
}