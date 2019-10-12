package cz.levinzonr.spotistats.repositories

import cz.levinzonr.spotistats.models.AccessToken

interface AuthTokenRepository {

    fun set(accessToken: AccessToken)
    fun get() : AccessToken?
    fun clear()
}