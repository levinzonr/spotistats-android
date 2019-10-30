package cz.levinzonr.spoton.domain.models

sealed class PlayerActionResult {
    object Success : PlayerActionResult()
    data class Error(
            val error: Throwable,
            val message: String) : PlayerActionResult()
}