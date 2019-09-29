package cz.levinzonr.spotistats.injection.modules

import cz.levinzonr.spotistats.domain.interactors.PostsInteractor
import org.koin.dsl.module

val interactorModule = module {
    single { PostsInteractor(get()) }
}