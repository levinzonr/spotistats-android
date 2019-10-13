package cz.levinzonr.spotistats.injection.modules

import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import cz.levinzonr.spotistats.BuildConfig
import cz.levinzonr.spotistats.network.Api
import cz.levinzonr.spotistats.network.AuthApi
import cz.levinzonr.spotistats.network.token.AppAuthenticator
import cz.levinzonr.spotistats.network.token.AuthTokenInterceptor
import cz.levinzonr.spotistats.network.util.DateDeserializer
import cz.levinzonr.spotistats.network.util.ItemTypeAdapterFactory
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import com.spotify.android.appremote.api.ConnectionParams
import cz.levinzonr.spotistats.models.SpotifyCredentials


val restModule = module {

    single(named(Constants.CLIENT_ID)) { BuildConfig.CLIENT_ID }
    single(named(Constants.URL_API_AUTH)) { BuildConfig.API_AUTH_URL }
    single(named(Constants.URL_API)) { BuildConfig.API_URL }
    single(named(Constants.CLIENT_SECRET)) { BuildConfig.CLIENT_SECRET }
    single(named(Constants.CLIENT_REDIRECT_URI)) { BuildConfig.REDIRECT_URI }


    factory {
        SpotifyCredentials(
                get(named(Constants.CLIENT_ID)),
                get(named(Constants.CLIENT_SECRET)),
                get(named(Constants.CLIENT_REDIRECT_URI))
        )
    }

    factory {
        val creds = get<SpotifyCredentials>()
        ConnectionParams.Builder(creds.clientId)
                .setRedirectUri(creds.redirectUri)
                .showAuthView(true)
                .build()
    }

    single<TypeAdapterFactory> { ItemTypeAdapterFactory() }


    single {
        GsonBuilder()
                .registerTypeAdapterFactory(ItemTypeAdapterFactory())
                .registerTypeAdapter(Date::class.java, DateDeserializer())
                .setDateFormat(DateDeserializer.DATE_FORMATS[0])
                .create()
    }

    single<Interceptor> { AuthTokenInterceptor(get()) }

    single<Authenticator> {
        AppAuthenticator(get(), get(named(Constants.AUTH_API)),
                get(named(Constants.CLIENT_ID)),
                get(named(Constants.CLIENT_SECRET)))
    }

    // Okhttp
    single(named("OKHTTP_AUTH")) {

        val clientBuilder = OkHttpClient.Builder()
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            clientBuilder.addInterceptor(logger)
        }

        clientBuilder.build()
    }

    single(named("OKHTTP_API")) {

        val clientBuilder = OkHttpClient.Builder()
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(AuthTokenInterceptor(get()))
                .authenticator(get())
        if (BuildConfig.DEBUG) {
            val logger = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }
            clientBuilder.addInterceptor(logger)
        }

        clientBuilder.build()
    }




    single(named(Constants.CLIENT_API)) {
        get<Retrofit>(named(Constants.RETROFIT_API)).create<Api>(Api::class.java)
    }

    single(named(Constants.AUTH_API)) {
        get<Retrofit>(named(Constants.RETROFIT_AUTH_API)).create<AuthApi>(AuthApi::class.java)
    }

    // Retrofit
    single(named(Constants.RETROFIT_API)) {
        Retrofit.Builder()
                .client(get(named("OKHTTP_API")))
                .baseUrl(get<String>(named(Constants.URL_API)))
                .addConverterFactory(GsonConverterFactory.create(get()))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }


    single(named(Constants.RETROFIT_AUTH_API)) {
        Retrofit.Builder()
                .client(get(named("OKHTTP_AUTH")))
                .baseUrl(get<String>(named(Constants.URL_API_AUTH)))
                .addConverterFactory(GsonConverterFactory.create(get()))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }
}