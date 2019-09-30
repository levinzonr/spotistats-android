package cz.levinzonr.spotistats.repositories

interface AuthTokenRepository {

    fun set(token: String)
    fun get() : String?
    fun clear()
}