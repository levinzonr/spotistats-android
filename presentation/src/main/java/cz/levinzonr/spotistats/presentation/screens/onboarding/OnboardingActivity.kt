package cz.levinzonr.spotistats.presentation.screens.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import cz.levinzonr.spotistats.presentation.R
import cz.levinzonr.spotistats.presentation.base.BaseActivity
import cz.levinzonr.spotistats.presentation.extensions.setDarkMode
import cz.levinzonr.spotistats.repositories.SettingsRepository
import org.koin.android.ext.android.inject

class OnboardingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
    }
}