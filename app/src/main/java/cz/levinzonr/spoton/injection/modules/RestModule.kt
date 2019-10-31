package cz.levinzonr.spoton.injection.modules

import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import cz.levinzonr.spoton.BuildConfig
import cz.levinzonr.spoton.network.SpotifyApi
import cz.levinzonr.spoton.network.SpotifyAuthApi
import cz.levinzonr.spoton.network.token.AppAuthenticator
import cz.levinzonr.spoton.network.token.AuthTokenInterceptor
import cz.levinzonr.spoton.network.util.DateDeserializer
import cz.levinzonr.spoton.network.util.ItemTypeAdapterFactory
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
import cz.levinzonr.spoton.models.SpotifyCredentials
import cz.levinzonr.spoton.network.LyricsApi


val restModule = module {

    single(named(Constants.CLIENT_ID)) { BuildConfig.CLIENT_ID }
    single(named(Constants.URL_API_AUTH)) { BuildConfig.API_AUTH_URL }
    single(named(Constants.URL_API)) { BuildConfig.API_URL }
    single(named(Constants.CLIENT_SECRET)) { BuildConfig.CLIENT_SECRET }
    single(named(Constants.CLIENT_REDIRECT_URI)) { BuildConfig.REDIRECT_URI }

    single(named(Constants.LYRICS_API_URL)) { BuildConfig.MUSICXMATCH_API_URL }
    single(named(Constants.LYRICS_KEY)) { BuildConfig.MUSICXMATCH_API_KEY }


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
                .showAuthView(false)
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

    // Okhttp
    single(named("OKHTTP_LYRICS")) {

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



    single(named(Constants.CLIENT_API)) {
        get<Retrofit>(named(Constants.RETROFIT_API)).create<SpotifyApi>(SpotifyApi::class.java)
    }

    single(named(Constants.AUTH_API)) {
        get<Retrofit>(named(Constants.RETROFIT_AUTH_API)).create<SpotifyAuthApi>(SpotifyAuthApi::class.java)
    }

    single(named(Constants.LYRICS_API)) {
        get<Retrofit>(named(Constants.RETROFIT_AUTH_API)).create<LyricsApi>(LyricsApi::class.java)
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

    single(named(Constants.RETROFIT_LYRICS)) {
        Retrofit.Builder()
                .client(get(named("OKHTTP_LYRICS")))
                .baseUrl(get<String>(named(Constants.LYRICS_API_URL)))
                .addConverterFactory(GsonConverterFactory.create(get()))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }
}