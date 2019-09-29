package cz.levinzonr.spotistats.injection.modules

import dagger.Binds
import dagger.Module
import cz.levinzonr.spotistats.domain.managers.PrefManager
import cz.levinzonr.spotistats.storage.PrefManagerImpl
import javax.inject.Singleton

@Module
abstract class StorageBindingModule {
    @Binds
    @Singleton
    abstract fun bindPrefManager(manager: PrefManagerImpl): PrefManager
}