package com.project.navigation

import kotlinx.serialization.Serializable

sealed interface IngredientRoute: Route {
    @Serializable
    data object BarcodeScanner : IngredientRoute

    @Serializable
    data object SaveIngredient : IngredientRoute

    @Serializable
    data object DirectInput : IngredientRoute
}
