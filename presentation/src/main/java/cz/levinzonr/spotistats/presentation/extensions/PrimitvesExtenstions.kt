package cz.levinzonr.spotistats.presentation.extensions

fun Double.toPercentageString() : String {
   return StringBuilder("%.2f".format(this)).append("%").toString()
}

fun Long.toMmSs() : String {
    val minutes = this / 1000 / 60
    val seconds = this / 1000 % 60
    return "$minutes:$seconds"
}