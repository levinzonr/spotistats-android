package cz.levinzonr.spotistats.injection.modules

import cz.levinzonr.spotistats.network.RestPostRepository
import cz.levinzonr.spotistats.repositories.PostRepository
import org.koin.dsl.module


val repositoryModule = module {

    single<PostRepository> { RestPostRepository(get()) }

}