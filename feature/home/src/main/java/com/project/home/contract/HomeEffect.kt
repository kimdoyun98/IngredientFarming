package com.project.home.contract

sealed interface HomeEffect {
    object NavigateToManage : HomeEffect
    object NavigateToBarcodeScanner : HomeEffect
    object NavigateToDirectInput : HomeEffect
    object NavigateToRecipe : HomeEffect
    object NavigateToShoppingCart : HomeEffect
}
