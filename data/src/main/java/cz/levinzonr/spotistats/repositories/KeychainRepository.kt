package cz.levinzonr.spotistats.repositories

import cz.levinzonr.spotistats.models.AccessToken

interface KeychainRepository {

   suspend fun obtainAccessToken(code: String) : AccessToken
}