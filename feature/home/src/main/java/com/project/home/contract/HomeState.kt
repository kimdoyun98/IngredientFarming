package com.project.home.contract

data class HomeState(
    val ingredientCount: Int =0,
    val expiresSoonCount: Int = 0,
    val recipeCount: Int = 0,
    val addStatus: Boolean = false,
)

