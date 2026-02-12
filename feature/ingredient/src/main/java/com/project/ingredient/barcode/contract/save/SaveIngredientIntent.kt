package com.project.ingredient.barcode.contract.save

sealed interface SaveIngredientIntent {
    object SaveButtonClick: SaveIngredientIntent
    data class IngredientCountChange(val count: Int): SaveIngredientIntent

    object DirectInputButtonClick: SaveIngredientIntent
}
