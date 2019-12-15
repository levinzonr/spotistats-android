package cz.levinzonr.spoton.injection.modules

import cz.levinzonr.spoton.domain.interactors.*
import cz.levinzonr.spoton.domain.interactors.player.AddContentToQueueInteractor
import cz.levinzonr.spoton.domain.interactors.player.PlayContentInteractor
import cz.levinzonr.spoton.domain.interactors.player.PlayNextInteractor
import cz.levinzonr.spoton.domain.interactors.player.PlayPreviousInteractor
import cz.levinzonr.spoton.domain.interactors.player.TogglePlayInteractor
import org.koin.core.qualifier.named
import org.koin.dsl.module

val interactorModule = module {
    factory { PostsInteractor(get(named(Constants.CLIENT_API))) }
    factory { LoginInteractor(get(), get())}
    factory { GetUserTopTracksInteractor(get())}
    factory { GetUserProfileInteractor(get(named(Constants.CLIENT_API)), get()) }
    factory { GetTrackFeatures(get(named(Constants.CLIENT_API))) }
    factory { GetRecommendedTracks(get(named(Constants.CLIENT_API))) }
    factory { GetTrackDetailsInteractor(get(named(Constants.CLIENT_API))) }
    factory { GetPlaylistsInteractor(get(named(Constants.CLIENT_API))) }
    factory { AddTracksToPlaylistInteractor(get(named(Constants.CLIENT_API))) }
    factory { AddTracksToNewPlaylistInteractor(get(), get(named(Constants.CLIENT_API))) }
    factory { AddContentToQueueInteractor(get()) }
    factory { PlayContentInteractor(get()) }
    factory { PlayNextInteractor(get()) }
    factory { PlayPreviousInteractor(get()) }
    factory { TogglePlayInteractor(get()) }
    factory { RefreshAppConfigInteractor(get()) }
    factory { ShouldShowUpdateReminderInteractor(get(), get(), get()) }
    factory { PlayPlaylistInteractor(get()) }
    factory { GetDeviceInfoInteractor(get()) }
}