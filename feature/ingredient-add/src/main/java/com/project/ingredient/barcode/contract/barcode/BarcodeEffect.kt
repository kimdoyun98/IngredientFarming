package com.project.ingredient.barcode.contract.barcode

import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore

sealed interface BarcodeEffect {
    data class NavigateSaveIngredientScreen(
        val name: String,
        val category: IngredientCategory = IngredientCategory.OTHER,
        val store: IngredientStore = IngredientStore.ROOM_TEMPERATURE
    ) : BarcodeEffect
    object NavigateDirectInputScreen : BarcodeEffect
    object BarcodeProductEmpty: BarcodeEffect
    object BarcodeResultError: BarcodeEffect
    object CameraPermissionDenied: BarcodeEffect
    data class CameraPermissionPermanentlyDenied(val openAppSettings: () -> Unit): BarcodeEffect
}
