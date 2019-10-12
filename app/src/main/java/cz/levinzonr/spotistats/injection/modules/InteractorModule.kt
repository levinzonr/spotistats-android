package cz.levinzonr.spotistats.injection.modules

import cz.levinzonr.spotistats.domain.interactors.GetRecommendedTracks
import cz.levinzonr.spotistats.domain.interactors.GetTrackDetailsInteractor
import cz.levinzonr.spotistats.domain.interactors.GetTrackFeatures
import cz.levinzonr.spotistats.domain.interactors.GetUserProfileInteractor
import cz.levinzonr.spotistats.domain.interactors.GetUserTopTracksInteractor
import cz.levinzonr.spotistats.domain.interactors.LoginInteractor
import cz.levinzonr.spotistats.domain.interactors.PostsInteractor
import org.koin.core.qualifier.named
import org.koin.dsl.module

val interactorModule = module {
    single { PostsInteractor(get()) }
    single { LoginInteractor(get(), get())}
    single { GetUserTopTracksInteractor(get())}
    single { GetUserProfileInteractor(get()) }
    single { GetTrackFeatures(get(named(Constants.CLIENT_API))) }
    single { GetRecommendedTracks(get(named(Constants.CLIENT_API))) }
    single { GetTrackDetailsInteractor(get(named(Constants.CLIENT_API))) }
}