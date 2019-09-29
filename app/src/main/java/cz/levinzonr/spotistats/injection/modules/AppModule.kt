package cz.levinzonr.spotistats.injection.modules

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.Provides
import cz.levinzonr.spotistats.App
import cz.levinzonr.spotistats.inititializers.AppInitializer
import cz.levinzonr.spotistats.inititializers.AppInitializerImpl

@Module
abstract class AppModule {

    @Binds
    abstract fun bindAppInitalizer(initializer: AppInitializerImpl): AppInitializer

    @Module
    companion object {
        @JvmStatic
        @Provides
        fun provideContext(application: App): Context = application.applicationContext
    }
}