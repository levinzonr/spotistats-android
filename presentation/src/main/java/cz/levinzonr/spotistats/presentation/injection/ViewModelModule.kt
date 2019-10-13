package cz.levinzonr.spotistats.presentation.injection

import cz.levinzonr.spotistats.presentation.screens.main.onrepeat.OnRepeatViewModel
import cz.levinzonr.spotistats.presentation.screens.main.profile.ProfileViewModel
import cz.levinzonr.spotistats.presentation.screens.main.settings.SettingsViewModel
import cz.levinzonr.spotistats.presentation.screens.main.trackdetails.TrackDetailsViewModel
import cz.levinzonr.spotistats.presentation.screens.onboarding.login.LoginViewModel
import cz.levinzonr.spotistats.presentation.screens.onboarding.splash.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModels = module {

    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { OnRepeatViewModel(get()) }
    viewModel { ProfileViewModel(get(), get(), get()) }
    viewModel { (id: String) -> TrackDetailsViewModel(id, get(), get(), get(), get()) }
    viewModel { SettingsViewModel(get()) }
}