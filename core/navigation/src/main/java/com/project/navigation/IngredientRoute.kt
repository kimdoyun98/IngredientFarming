package com.project.navigation

import kotlinx.serialization.Serializable

sealed interface IngredientRoute : Route {

    @Serializable
    data object Home : IngredientRoute

    @Serializable
    data object BarcodeScanner : IngredientRoute

    @Serializable
    data class SaveIngredient(
        val name: String,
        val count: Int,
        val expirationDate: String,
        val storeSelected: Int?,
        val categorySelected: Int?,
    ) : IngredientRoute

    @Serializable
    data object DirectInput : IngredientRoute
}
