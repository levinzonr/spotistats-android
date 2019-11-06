package cz.levinzonr.spoton.presentation.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.util.AttributeSet
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FloatingImageView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : FloatingActionButton(context, attrs, defStyleAttr) {

    private val clipPath = Path()
    private val dstRect = Rect()
    private val rectF = RectF()
    private val radius = 14f

    override fun onDraw(canvas: Canvas) {

        canvas.getClipBounds(dstRect) // get canvas size base screen size
        rectF.set(dstRect)

        clipPath.addRoundRect(rectF, radius, radius, Path.Direction.CW)
        canvas.clipPath(clipPath)

        val bitmap = (drawable as? BitmapDrawable?)?.bitmap ?: return
        canvas.drawBitmap(bitmap, null, dstRect, null)


    }
}
