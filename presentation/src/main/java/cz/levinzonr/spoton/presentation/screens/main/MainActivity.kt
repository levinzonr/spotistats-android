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
import android.net.Uri


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
                .setTitle(getString(R.string.update_title))
                .setMessage(getString(R.string.update_message))
                .setPositiveButton(getString(R.string.update_now_button)) {
                    openPlayStore()
                }
                .setNegativeButton(getString(R.string.update_later_button)) {
                    it.dismiss()
                }
                .setCancelable(false)
                .show()
    }

    private fun openPlayStore() {
        val appPackageName = packageName // getPackageName() from Context or Activity object
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
        } catch (anfe: android.content.ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
        }
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, MainActivity::class.java)
                .apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
    }


}
