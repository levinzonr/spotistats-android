package cz.levinzonr.spotistats.storage

import cz.levinzonr.spotistats.repositories.AuthTokenRepository
import cz.levinzonr.spotistats.repositories.PrefManager

class AuthTokenRepositoryImpl(private val prefManager: PrefManager) : AuthTokenRepository {

    override fun set(token: String) {
        prefManager.setString(PREF_TOKEN, token)
    }

    override fun get(): String? {
       return prefManager.getString(PREF_TOKEN, null)
    }

    override fun clear() {
        prefManager.remove(PREF_TOKEN)
    }

    companion object {
        private const val PREF_TOKEN = "pref::token"
    }
}