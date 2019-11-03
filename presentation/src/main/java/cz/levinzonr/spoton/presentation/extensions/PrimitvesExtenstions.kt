package cz.levinzonr.spoton.presentation.extensions

fun Double.toPercentageString() : String {
    val toFormat = if (this <= 1) this * 100.0 else this
   return StringBuilder("%.0f".format(toFormat)).append("%").toString()
}

fun Long.toMmSs() : String {
    val minutes = this / 1000 / 60
    val seconds = this / 1000 % 60
    return "$minutes:${
        if (seconds < 10) "0$seconds" else seconds.toString()
    }"
}