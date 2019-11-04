package cz.levinzonr.spoton.presentation.screens.onboarding.login

import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import cz.levinzonr.spoton.presentation.R
import cz.levinzonr.spoton.presentation.extensions.inflate
import kotlinx.android.synthetic.main.view_onboarding.view.*

class OnboardingAdapter(var onLoginClicked: (() -> Unit)? = null) : RecyclerView.Adapter<OnboardingAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.view_onboarding))
    }

    override fun getItemCount(): Int {
        return 3
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewAtPosition(position)
    }

    inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindViewAtPosition(position: Int) {
            view.loginButton.isVisible = position == itemCount - 1
            when(position) {
                0 -> {
                    view.onboardingTitle.setText(R.string.onboarding_title_1)
                    view.onboardingMessage.setText(R.string.onboarding_message_1)
                    view.imageView.setImageResource(R.drawable.ic_album_black_24dp)
                }
                1 -> {
                    view.onboardingTitle.setText(R.string.onboarding_title_2)
                    view.onboardingMessage.setText(R.string.onboarding_message_2)
                    view.imageView.setImageResource(R.drawable.ic_audiotrack_black_24dp)

                }
                else -> {
                    view.onboardingTitle.setText(R.string.onboarding_title_3)
                    view.onboardingMessage.setText(R.string.onboarding_message_3)
                    view.imageView.setImageResource(R.drawable.spotify_icon_rgb_green)

                }
            }
            view.loginButton.setOnClickListener { onLoginClicked?.invoke() }
        }
    }
}