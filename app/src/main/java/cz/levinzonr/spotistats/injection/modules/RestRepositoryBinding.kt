package cz.levinzonr.spotistats.injection.modules

import cz.levinzonr.spotistats.network.RestPostRepository
import cz.levinzonr.spotistats.network.RestUserRepository
import cz.levinzonr.spotistats.network.RestUserTopRepository
import cz.levinzonr.spotistats.repositories.KeychainRepository
import cz.levinzonr.spotistats.repositories.PostRepository
import cz.levinzonr.spotistats.repositories.RestKeychainRepository
import cz.levinzonr.spotistats.repositories.UserRepository
import cz.levinzonr.spotistats.repositories.UserTopRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module


val repositoryModule = module {

    single<PostRepository> { RestPostRepository(get(named(Constants.CLIENT_API))) }

    single<UserTopRepository> { RestUserTopRepository(get(named(Constants.CLIENT_API))) }

    single<UserRepository> { RestUserRepository(get(named(Constants.CLIENT_API))) }

    single<KeychainRepository> {
        RestKeychainRepository(get(named(Constants.AUTH_API)),
                get(named(Constants.CLIENT_ID)),
                get(named(Constants.CLIENT_SECRET))
        )
    }
}