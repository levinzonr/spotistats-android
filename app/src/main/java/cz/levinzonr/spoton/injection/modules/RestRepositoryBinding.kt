package cz.levinzonr.spoton.injection.modules

import cz.levinzonr.spoton.network.RestPostRepository
import cz.levinzonr.spoton.network.RestUserRepository
import cz.levinzonr.spoton.network.RestUserTopRepository
import cz.levinzonr.spoton.repositories.KeychainRepository
import cz.levinzonr.spoton.repositories.PostRepository
import cz.levinzonr.spoton.repositories.RestKeychainRepository
import cz.levinzonr.spoton.repositories.UserRepository
import cz.levinzonr.spoton.repositories.UserTopRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module


val repositoryModule = module {

    single<PostRepository> { RestPostRepository(get(named(Constants.CLIENT_API))) }

    single<UserTopRepository> { RestUserTopRepository(get(named(Constants.CLIENT_API))) }

    single<UserRepository> { RestUserRepository(get(named(Constants.CLIENT_API))) }

    single<KeychainRepository> {
        RestKeychainRepository(get(named(Constants.AUTH_API)),
             get(), get()
        )
    }
}