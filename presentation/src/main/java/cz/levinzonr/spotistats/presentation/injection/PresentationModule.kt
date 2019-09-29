package cz.levinzonr.spotistats.presentation.injection

import dagger.Module
import cz.levinzonr.spotistats.presentation.ui.main.MainActivityBuilder
import cz.levinzonr.spotistats.presentation.ui.splash.SplashBuilder

@Module(includes = [
    MainActivityBuilder::class,
    SplashBuilder::class
])
class PresentationModule