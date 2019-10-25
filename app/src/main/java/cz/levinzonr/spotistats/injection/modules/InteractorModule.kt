package cz.levinzonr.spotistats.injection.modules

import cz.levinzonr.spotistats.domain.interactors.*
import org.koin.core.qualifier.named
import org.koin.dsl.module

val interactorModule = module {
    factory { PostsInteractor(get(named(Constants.CLIENT_API))) }
    factory { LoginInteractor(get(), get())}
    factory { GetUserTopTracksInteractor(get())}
    factory { GetUserProfileInteractor(get()) }
    factory { GetTrackFeatures(get(named(Constants.CLIENT_API))) }
    factory { GetRecommendedTracks(get(named(Constants.CLIENT_API))) }
    factory { GetTrackDetailsInteractor(get(named(Constants.CLIENT_API))) }
    factory { GetPlaylistsInteractor(get(named(Constants.CLIENT_API))) }
    factory { AddTracksToPlaylistInteractor(get(named(Constants.CLIENT_API))) }
}