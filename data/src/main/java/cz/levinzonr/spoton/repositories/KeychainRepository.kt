package cz.levinzonr.spoton.repositories

import cz.levinzonr.spoton.models.AccessToken

interface KeychainRepository {

   suspend fun obtainAccessToken(code: String) : AccessToken
}