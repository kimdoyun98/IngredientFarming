package com.project.ui.util

import com.project.model.recipe.IngredientUnit

fun Double.format(unit: IngredientUnit = IngredientUnit.COUNT): String {
    val intPart = this.toInt()
    val hasHalf = (this - intPart) >= 0.5

    return when {
        intPart == 0 && hasHalf -> "반 ${unit.label}"
        hasHalf -> "${intPart}${unit.label} 반"
        else -> "$intPart${unit.label}"
    }
}
