package cz.levinzonr.spoton.presentation.extensions

fun Double.toPercentageString() : String {
   return StringBuilder("%.0f".format(this)).append("%").toString()
}

fun Long.toMmSs() : String {
    val minutes = this / 1000 / 60
    val seconds = this / 1000 % 60
    return "$minutes:${
        if (seconds < 10) "0$seconds" else seconds.toString()
    }"
}