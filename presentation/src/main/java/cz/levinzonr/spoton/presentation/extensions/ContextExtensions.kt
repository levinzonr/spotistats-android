package cz.levinzonr.spoton.presentation.extensions

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import cz.levinzonr.spoton.models.DarkMode
import cz.levinzonr.spoton.presentation.base.BaseOptionsDialog
import timber.log.Timber



fun ViewGroup.asSequence(): Sequence<View> = object : Sequence<View> {
    override fun iterator(): Iterator<View> = object : Iterator<View> {
        private var nextValue: View? = null
        private var done = false
        private var position: Int = 0

        override fun hasNext(): Boolean {
            if (nextValue == null && !done) {
                nextValue = getChildAt(position)
                position++
                if (nextValue == null) done = true
            }
            return nextValue != null
        }

        override fun next(): View {
            if (!hasNext()) {
                throw NoSuchElementException()
            }
            val answer = nextValue
            nextValue = null
            return answer!!
        }
    }
}

fun Fragment.hideKeyboard() {
    activity?.hideKeyboard()
}

fun Activity.hideKeyboard() {
    val view = currentFocus
    if (view != null) {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }
}

inline fun<reified T: BaseOptionsDialog> Fragment.showOptionsDialog(crossinline onClick: (Int) -> Unit) {
    BaseOptionsDialog.Builder(T::class).show(fragmentManager!!) {
        onClick.invoke(it)
    }
}

fun AppCompatActivity.setDarkMode(darkMode: DarkMode) {
    AppCompatDelegate.setDefaultNightMode(when (darkMode) {
        DarkMode.Enabled -> AppCompatDelegate.MODE_NIGHT_YES
        DarkMode.Disabled -> AppCompatDelegate.MODE_NIGHT_NO
        DarkMode.FollowSystem -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    })
}

fun Application.setDarkMode(darkMode: DarkMode) {
    AppCompatDelegate.setDefaultNightMode(when (darkMode) {
        DarkMode.Enabled -> AppCompatDelegate.MODE_NIGHT_YES
        DarkMode.Disabled -> AppCompatDelegate.MODE_NIGHT_NO
        DarkMode.FollowSystem -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    })
}

fun Fragment.setDarkMode(darkMode: DarkMode) {
    (activity as? AppCompatActivity)?.setDarkMode(darkMode)
}

fun View.dpToPx(dp: Int): Int {
    return context.dpToPx(dp)
}

fun ViewGroup.inflate(@LayoutRes id: Int): View {
    return LayoutInflater.from(context).inflate(id, this, false)
}

fun Fragment.dpToPx(dp: Int): Int {
    return requireContext().dpToPx(dp)
}

fun Context.dpToPx(dp: Int): Int {
    return (dp * (resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}

fun <T> List<T>.first(n: Int = 3) : List<T> {
    return if (size <= n) this else subList(0, n)
}


fun Fragment.showToast(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

