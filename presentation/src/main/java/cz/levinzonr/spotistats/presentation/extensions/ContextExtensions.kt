package cz.levinzonr.spotistats.presentation.extensions

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import cz.levinzonr.spotistats.models.DarkMode
import timber.log.Timber

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
