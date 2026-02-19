package com.project.home.contract

sealed interface HomeIntent {
    object OnManageButtonClick : HomeIntent
    object OnAddButtonClick : HomeIntent
    object OnDismissRequestToAdd : HomeIntent
    object OnDirectInputButtonClick : HomeIntent
    object OnBarcodeScannerButtonClick : HomeIntent
    object OnRecipeButtonClick : HomeIntent
    object OnShoppingCartButtonClick : HomeIntent
}
