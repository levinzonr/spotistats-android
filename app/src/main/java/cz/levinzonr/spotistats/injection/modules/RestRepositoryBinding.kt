package cz.levinzonr.spotistats.injection.modules

import cz.levinzonr.spotistats.network.RestPostRepository
import cz.levinzonr.spotistats.network.RestUserRepository
import cz.levinzonr.spotistats.network.RestUserTopRepository
import cz.levinzonr.spotistats.repositories.PostRepository
import cz.levinzonr.spotistats.repositories.UserRepository
import cz.levinzonr.spotistats.repositories.UserTopRepository
import org.koin.dsl.module


val repositoryModule = module {

    single<PostRepository> { RestPostRepository(get()) }

    single<UserTopRepository> { RestUserTopRepository(get()) }

    single<UserRepository> { RestUserRepository(get()) }
}