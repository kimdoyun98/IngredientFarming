package com.project.ingredient.barcode.contract.directInput

import androidx.compose.runtime.Immutable

@Immutable
data class DirectInputState(
    val name: String = "",
    val expirationDate: String = "",
    val storeSelected: Int? = null,
    val categorySelected: Int? = null,
){
    fun isEnabled(): Boolean{
        return name.isNotEmpty() &&
                storeSelected != null &&
                categorySelected != null &&
                (expirationDate.isEmpty() || expirationDate.length == 10)
    }
}
