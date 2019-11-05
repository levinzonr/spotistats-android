package cz.levinzonr.spoton.presentation.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.IdRes
import com.google.android.material.card.MaterialCardView
import cz.levinzonr.spoton.presentation.R
import kotlinx.android.synthetic.main.view_score_item.view.*

/**
 * TODO: document your custom view class.
 */
class ScoreItemView @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defStyle: Int = 0
) : FrameLayout(context, attributeSet, defStyle) {


    init {
        View.inflate(context, R.layout.view_score_item, this)
        attributeSet?.let(this::initFromAttrs)
    }

    var value: String
        get() = scoreValueTv.text?.toString() ?: ""
        set(value) { scoreValueTv.text = value }

    private fun initFromAttrs(attributeSet: AttributeSet) {

    }


    fun setScore(label: Int, value: String) {
        scoreTitleTv.setText(label)
        scoreValueTv.text = value
    }
}

fun ScoreItemView.setKey(key: Int) {
    val keyString = when(key) {
        0 -> "C/B♯"
        1 -> "C♯/D♭"
        2 -> "D"
        3 -> "D♯/E♭"
        4 -> "E/F♭"
        5 -> "F/E♯"
        6 -> "F♯/G♭"
        7 -> "G"
        8 -> "G♯/A♭"
        9 -> "A"
        10 -> "A♯/B♭"
        else -> "B"
    }
    setScore(R.string.track_feature_key, keyString)
}
