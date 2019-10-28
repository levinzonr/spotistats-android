package cz.levinzonr.spoton.repositories

import cz.levinzonr.spoton.models.AccessToken

interface AuthTokenRepository {

    fun set(accessToken: AccessToken)
    fun get() : AccessToken?
    fun clear()
}