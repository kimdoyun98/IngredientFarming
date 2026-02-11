package com.project.ingredient.barcode.contract.directInput

sealed interface DirectInputEffect {
       object NavigateToBack : DirectInputEffect
         object NavigateToSaveIngredient : DirectInputEffect
}
