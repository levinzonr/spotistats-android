package cz.levinzonr.spotistats.presentation.ui.main

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import cz.levinzonr.spotistats.presentation.injection.ViewModelKey

@Module
internal abstract class MainActivityBuilder {

    @ContributesAndroidInjector
    internal abstract fun mainActivity(): MainActivity
}