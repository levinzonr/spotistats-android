package cz.levinzonr.spotistats.presentation.ui.main

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import cz.levinzonr.spotistats.presentation.injection.ViewModelKey
import cz.levinzonr.spotistats.presentation.ui.sample.SampleBuilder

@Module
internal abstract class MainActivityBuilder {

    @Binds
    @IntoMap
    @ViewModelKey(MainActivityViewModel::class)
    abstract fun bindMainActivityViewMode(viewModel: MainActivityViewModel): ViewModel

    @ContributesAndroidInjector(
        modules = [
            SampleBuilder::class
        ]
    )
    internal abstract fun mainActivity(): MainActivity
}