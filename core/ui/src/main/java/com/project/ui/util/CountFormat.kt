package com.project.ui.util

fun Double.format(unit: String = "개"): String {
    val intPart = this.toInt()
    val hasHalf = (this - intPart) >= 0.5

    return when {
        intPart == 0 && hasHalf -> "반 $unit"
        hasHalf -> "${intPart}$unit 반"
        else -> "$intPart$unit"
    }
}
