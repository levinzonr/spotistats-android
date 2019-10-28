package cz.levinzonr.spotistats.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.view.isVisible
import cz.levinzonr.spotistats.presentation.R
import kotlinx.android.synthetic.main.view_settings_button.view.*

class SettingsButtonView @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle) {

    init {
        View.inflate(context, R.layout.view_settings_button, this)
        init(attributeSet) }

    private fun init(attributeSet: AttributeSet?) {
        attributeSet?.let(this::initFromAttributes)
    }

    var value: String
        get() = preferenceValueTv.text.toString()
        set(value) {
            preferenceValueTv.text = value
            preferenceValueTv.isVisible = true
        }


    private fun initFromAttributes(attributeSet: AttributeSet) {
        val attrs = context.obtainStyledAttributes(attributeSet, R.styleable.SettingsButtonView)
        preferenceTv.text = attrs.getString(R.styleable.SettingsButtonView_text)
        val showSection = attrs.getBoolean(R.styleable.SettingsButtonView_showSection, false)
        if (showSection) {
            val icon = attrs.getResourceId(R.styleable.SettingsButtonView_sectionIcon, 0)
            val text = attrs.getString(R.styleable.SettingsButtonView_sectionName)
            sectionIconIv.setImageResource(icon)
            sectionNameTv.text = text
        }
        sectionNameTv.isVisible = showSection
        sectionIconIv.isVisible = showSection
        attrs.recycle()
    }

    override fun setOnClickListener(l: OnClickListener?) {
        preferenceButton.setOnClickListener(l)
    }
}