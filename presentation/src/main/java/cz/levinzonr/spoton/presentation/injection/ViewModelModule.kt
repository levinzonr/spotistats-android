package cz.levinzonr.spoton.presentation.injection

import cz.levinzonr.spoton.presentation.screens.main.onrepeat.OnRepeatViewModel
import cz.levinzonr.spoton.presentation.screens.main.player.PlayerViewModel
import cz.levinzonr.spoton.presentation.screens.main.playlists.PlaylistsViewModel
import cz.levinzonr.spoton.presentation.screens.main.profile.ProfileViewModel
import cz.levinzonr.spoton.presentation.screens.main.settings.SettingsViewModel
import cz.levinzonr.spoton.presentation.screens.main.trackdetails.TrackDetailsViewModel
import cz.levinzonr.spoton.presentation.screens.onboarding.login.LoginViewModel
import cz.levinzonr.spoton.presentation.screens.onboarding.splash.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val viewModels = module {

    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { OnRepeatViewModel(get(), get()) }
    viewModel { ProfileViewModel(get(), get(), get()) }
    viewModel { (id: String) -> TrackDetailsViewModel(id, get(), get(), get(), get()) }
    viewModel { SettingsViewModel(get(), get()) }
    viewModel { (id: Array<String>) -> PlaylistsViewModel(id, get(), get()) }
    viewModel { PlayerViewModel(get(), get()) }
}