package cz.levinzonr.spotistats.presentation.ui.sample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import cz.levinzonr.spotistats.presentation.R
import cz.levinzonr.spotistats.presentation.extensions.observeNonNull
import cz.levinzonr.spotistats.presentation.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_sample.*

class SampleFragment : BaseFragment() {

    private val viewModel by viewModel<SampleViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchPosts()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sample, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.viewState.observeNonNull(this) { state ->
            showLoading(state)
            showPosts(state)
            showErrorMessage(state)
        }
    }

    private fun showPosts(state: SampleViewState) {
        postsTextView.text = state.posts.joinToString { it.title + System.lineSeparator() }
    }

    private fun showLoading(state: SampleViewState) {
        postsProgressBar.isVisible = state.isLoading
    }

    private fun showErrorMessage(state: SampleViewState) {
        defaultErrorController.get().showErrorSnackbar(requireView(), state.viewError?.consume() ?: return) {
            viewModel.fetchPosts()
        }
    }
}