package cz.levinzonr.spotistats.presentation.screens.main.profile


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.api.load
import coil.transform.CircleCropTransformation
import com.spotify.protocol.types.PlayerState
import cz.levinzonr.spotistats.domain.models.UserProfile
import cz.levinzonr.spotistats.models.PlaylistResponse
import cz.levinzonr.spotistats.models.TrackResponse
import cz.levinzonr.spotistats.models.UserResponse
import cz.levinzonr.spotistats.presentation.R
import cz.levinzonr.spotistats.presentation.base.BaseFragment
import cz.levinzonr.spotistats.presentation.util.VerticalSpaceDecoration
import kotlinx.android.synthetic.main.fragment_profile.*
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : BaseFragment<State>(), RecentPlaylistsAdapter.RecentPlaylistItemListener {

    private lateinit var adapter: RecentPlaylistsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }


    override val viewModel: ProfileViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        recentPlaylistsRv.addItemDecoration(VerticalSpaceDecoration(8))

        settingsBtn.setOnClickListener { viewModel.dispatch(Action.SettingsPressed) }
    }

    override fun onPlayOrderedClicked(playlist: PlaylistResponse) {


    }

    override fun onPlayShuffledClicked(playlist: PlaylistResponse) {

    }

    private fun setupRecyclerView() {
        adapter = RecentPlaylistsAdapter()
        recentPlaylistsRv.adapter = adapter
        adapter.listener = this
    }

    override fun renderState(state: State) {
        state.user?.let(this::renderProfile)
        adapter.submitList(state.recentPlaylists)
    }

    private fun renderProfile(user: UserProfile) {
        userDisplayNameTv.text = user.displayName
        userFollowersCountTv.setScore("Followers", user.followersCount.toString())
        userPlaylistsCountView.setScore("Playlists", user.playlistCount.toString())
        userAvaterIv.load(user.imageUrl) {
            transformations(CircleCropTransformation())
        }
    }




}
