package cz.levinzonr.spotistats.injection.modules

import cz.levinzonr.spotistats.domain.interactors.GetUserProfileInteractor
import cz.levinzonr.spotistats.domain.interactors.GetUserTopTracksInteractor
import cz.levinzonr.spotistats.domain.interactors.LoginInteractor
import cz.levinzonr.spotistats.domain.interactors.PostsInteractor
import org.koin.dsl.module

val interactorModule = module {
    single { PostsInteractor(get()) }
    single { LoginInteractor(get())}
    single { GetUserTopTracksInteractor(get())}
    single { GetUserProfileInteractor(get()) }
}