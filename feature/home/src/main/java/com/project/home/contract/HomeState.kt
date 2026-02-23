package com.project.home.contract

import androidx.compose.runtime.Immutable

@Immutable
data class HomeState(
    val ingredientCount: Int = 0,
    val expiresSoonCount: Int = 0,
    val recipeCount: Int = 0,
    val addStatus: Boolean = false,
)
