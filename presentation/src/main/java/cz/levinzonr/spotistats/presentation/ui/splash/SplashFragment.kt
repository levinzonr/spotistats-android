package cz.levinzonr.spotistats.presentation.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cz.levinzonr.spotistats.presentation.R
import cz.levinzonr.spotistats.presentation.ui.base.BaseFragment
import org.koin.android.viewmodel.ext.android.viewModel

class SplashFragment : BaseFragment<State>() {

    override val viewModel: SplashViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun renderState(state: State) {

    }

}