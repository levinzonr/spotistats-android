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
            view.loginButton.setOnClickListener { onLoginClicked?.invoke() }
        }
    }
}