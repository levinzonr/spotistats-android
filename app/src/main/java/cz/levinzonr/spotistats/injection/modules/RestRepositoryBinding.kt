package cz.levinzonr.spotistats.injection.modules

import dagger.Binds
import dagger.Module
import cz.levinzonr.spotistats.network.RestPostRepository
import cz.levinzonr.spotistats.repositories.PostRepository
import javax.inject.Singleton

@Module
abstract class RestRepositoryBinding {
    @Binds
    @Singleton
    abstract fun bindPostRepository(repository: RestPostRepository): PostRepository
}