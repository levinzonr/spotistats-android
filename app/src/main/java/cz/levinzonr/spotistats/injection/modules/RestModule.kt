package cz.levinzonr.spotistats.injection.modules

import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import cz.levinzonr.spotistats.BuildConfig
import cz.levinzonr.spotistats.network.Api
import cz.levinzonr.spotistats.network.util.DateDeserializer
import cz.levinzonr.spotistats.network.util.ItemTypeAdapterFactory
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.koin.experimental.builder.single
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit


val restModule = module {

    single(named("SPOTIFY_CLIENT_ID")) { BuildConfig.CLIENT_ID }

    single { ItemTypeAdapterFactory() }

    single { DateDeserializer() }

    single {
        GsonBuilder()
                .registerTypeAdapterFactory(get())
                .registerTypeAdapter(Date::class.java, get())
                .setDateFormat(DateDeserializer.DATE_FORMATS[0])
                .create()
    }

    // Okhttp
    single<OkHttpClient> {

        val clientBuilder = OkHttpClient.Builder()
                .connectTimeout(45, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
        if (BuildConfig.DEBUG) {
        }

        clientBuilder.build()
    }

    single {
        GsonConverterFactory.create(get())
    }

    single {
        get<Retrofit>().create<Api>(Api::class.java)
    }

    // Retrofit
    single<Retrofit> {
        Retrofit.Builder()
                .client(get())
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(get())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }
}