package com.project.ingredient.barcode.contract.directInput

sealed interface DirectInputIntent {
    object TopAppBarNavigationClick: DirectInputIntent
    data class StoreFilterChipSelect(val selected: Int): DirectInputIntent
    data class CategoryFilterChipSelect(val selected: Int): DirectInputIntent
    object NextButtonClick: DirectInputIntent
    data class NameInputChange(val name: String): DirectInputIntent

    data class CountInputChange(val count: String): DirectInputIntent
    data class ExpirationDateInputChange(val expirationDate: String): DirectInputIntent
}
