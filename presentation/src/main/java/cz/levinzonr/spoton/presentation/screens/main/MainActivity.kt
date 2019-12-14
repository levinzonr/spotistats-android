package cz.levinzonr.spoton.presentation.screens.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import cz.levinzonr.spoton.presentation.R
import cz.levinzonr.spoton.presentation.base.BaseActivity
import cz.levinzonr.spoton.presentation.extensions.observeNonNull
import cz.levinzonr.spoton.presentation.views.AppDialog
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigationView.setupWithNavController(findNavController(R.id.nav_host_fragment))
        viewModel.observableState.observeNonNull(this) { state ->
            state.updateReminderDialog?.consume()?.let {
               showUpdateDialog()
            }
        }
    }


    private fun showUpdateDialog() {
        AppDialog.Builder(this)
                .setMessage("Update Available")
                .show()
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, MainActivity::class.java)
                .apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
    }


}
