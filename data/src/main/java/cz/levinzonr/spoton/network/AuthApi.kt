package cz.levinzonr.spoton.network

import cz.levinzonr.spoton.models.AccessToken
import cz.levinzonr.spoton.models.AuthBody
import kotlinx.coroutines.Deferred
import retrofit2.Call
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


    @FormUrlEncoded
    @POST("api/token")
    fun refreshAccessToken(
            @Field("refresh_token") refreshToken: String,
            @Header("Authorization") authHeader: String,
            @Field("grant_type") grant: String = "refresh_token"
    ) : Call<AccessToken>


}