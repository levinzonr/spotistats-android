package cz.levinzonr.spoton.presentation.screens.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import cz.levinzonr.spoton.presentation.R
import cz.levinzonr.spoton.presentation.base.BaseActivity
import cz.levinzonr.spoton.presentation.extensions.setDarkMode
import cz.levinzonr.spoton.repositories.SettingsRepository
import org.koin.android.ext.android.inject

class OnboardingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)
    }
}