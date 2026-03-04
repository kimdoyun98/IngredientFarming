package com.project.shopping_cart.contract

sealed interface ShoppingCartIntent {
    object OnTopAppBarNavigationButtonClick: ShoppingCartIntent
    object OnItemAddContentShowButtonClick: ShoppingCartIntent
    data class OnAddItemNameChanged(val name: String): ShoppingCartIntent
    data class OnAddItemCountChanged(val count: String): ShoppingCartIntent
    data class OnAddItemCategoryChanged(val index: Int): ShoppingCartIntent
    object OnItemAddCancelButtonClick: ShoppingCartIntent
    object OnItemAddButtonClick: ShoppingCartIntent
    data class OnItemCheckBoxClick(val index: Int): ShoppingCartIntent
    data class OnItemDeleteClick(val index: Int): ShoppingCartIntent
    object OnSaveCartItemsButtonClick: ShoppingCartIntent
}
