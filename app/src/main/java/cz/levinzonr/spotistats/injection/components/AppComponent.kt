package cz.levinzonr.spotistats.injection.components

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import cz.levinzonr.spotistats.App
import cz.levinzonr.spotistats.injection.modules.AppModule
import cz.levinzonr.spotistats.injection.modules.ExecutorModule
import cz.levinzonr.spotistats.injection.modules.InteractorModule
import cz.levinzonr.spotistats.injection.modules.RestModule
import cz.levinzonr.spotistats.injection.modules.RestRepositoryBinding
import cz.levinzonr.spotistats.injection.modules.StorageBindingModule
import cz.levinzonr.spotistats.presentation.injection.PresentationModule
import cz.levinzonr.spotistats.presentation.injection.ViewModelBuilder
import javax.inject.Singleton

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ViewModelBuilder::class,
        PresentationModule::class,
        AppModule::class,
        ExecutorModule::class,
        InteractorModule::class,
        RestModule::class,
        RestRepositoryBinding::class,
        StorageBindingModule::class
    ]
)
@Singleton
interface AppComponent : AndroidInjector<App> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<App>
}