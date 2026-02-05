package com.project.navigation

import kotlinx.serialization.Serializable

sealed interface IngredientRoute: Route {
    @Serializable
    data object BarcodeScanner : IngredientRoute
}
