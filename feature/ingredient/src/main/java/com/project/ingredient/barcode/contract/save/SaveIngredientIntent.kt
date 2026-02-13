package com.project.ingredient.barcode.contract.save

sealed interface SaveIngredientIntent {
    object SaveButtonClick : SaveIngredientIntent
    data class UpdateButtonClick(val index: Int) : SaveIngredientIntent
    object PlusButtonClick : SaveIngredientIntent
    object DirectInputButtonClick : SaveIngredientIntent
    object UpdateBottomSheetDisMiss : SaveIngredientIntent
    object AddBottomSheetDisMiss : SaveIngredientIntent

    sealed interface BottomSheetIntent {
        object BarcodeScannerClick : BottomSheetIntent
        object DirectInputClick : BottomSheetIntent
        data class StoreFilterChipSelect(val selected: Int) : BottomSheetIntent
        data class CategoryFilterChipSelect(val selected: Int) : BottomSheetIntent
        data class NameInputChange(val name: String) : BottomSheetIntent
        data class CountInputChange(val count: String) : BottomSheetIntent
        data class ExpirationDateInputChange(val expirationDate: String) : BottomSheetIntent

        object UpdateButtonClick : BottomSheetIntent
    }
}
