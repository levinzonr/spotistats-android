package cz.levinzonr.spoton.injection.modules

import cz.levinzonr.spoton.repositories.PrefManager
import cz.levinzonr.spoton.domain.managers.UserManager
import cz.levinzonr.spoton.domain.managers.UserManagerImpl
import cz.levinzonr.spoton.repositories.AuthTokenRepository
import cz.levinzonr.spoton.repositories.SettingsRepository
import cz.levinzonr.spoton.repositories.SettingsRepositoryImpl
import cz.levinzonr.spoton.storage.AuthTokenRepositoryImpl
import cz.levinzonr.spoton.storage.PrefManagerImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module



val storageModule = module {
    single<PrefManager> { PrefManagerImpl(androidContext()) }
    single<UserManager> { UserManagerImpl(get()) }
    single<AuthTokenRepository> { AuthTokenRepositoryImpl(get(), get())}
    single<SettingsRepository> { SettingsRepositoryImpl(get()) }
}