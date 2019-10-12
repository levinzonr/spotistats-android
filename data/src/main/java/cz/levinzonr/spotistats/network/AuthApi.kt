package cz.levinzonr.spotistats.network

import cz.levinzonr.spotistats.models.AccessToken
import cz.levinzonr.spotistats.models.AuthBody
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface AuthApi {

    @FormUrlEncoded
    @POST("api/token")
    fun obtainAccessTokenAsync(
            @Field("code") code: String,
            @Field("client_id") clientId: String,
            @Field("client_secret") clientSecret: String,
            @Field("grant_type") grant: String = "authorization_code",
            @Field("redirect_uri") uri: String = "yourcustomprotocol://callback"
    ) : Deferred<AccessToken>

}