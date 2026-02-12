package com.project.ingredient.barcode.contract.save

sealed interface SaveIngredientEffect {
    object SaveIngredient: SaveIngredientEffect
    object NavigateToBarcodeScannerScreen: SaveIngredientEffect
    object NavigateToDirectInputScreen: SaveIngredientEffect
}
