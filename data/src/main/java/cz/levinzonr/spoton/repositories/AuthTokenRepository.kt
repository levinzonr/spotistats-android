package cz.levinzonr.spoton.repositories

import cz.levinzonr.spoton.models.AccessToken

interface AuthTokenRepository {

    var accessToken: String?
    var refreshToken: String?

    fun set(accessToken: AccessToken)

    fun clear()
}