package cz.levinzonr.spotistats.presentation.injection

import cz.levinzonr.spotistats.presentation.ui.main.onrepeat.OnRepeatViewModel
import cz.levinzonr.spotistats.presentation.ui.onboarding.login.LoginViewModel
import cz.levinzonr.spotistats.presentation.ui.onboarding.splash.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModels = module {

    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get(named("SPOTIFY_CLIENT_ID")), get()) }
    viewModel { OnRepeatViewModel(get()) }
}