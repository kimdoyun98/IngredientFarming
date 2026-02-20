package com.project.ingredient_manage.contract

sealed interface ManageIntent {
    object OnClickTopAppBarNavigation: ManageIntent
    object OnClickTopAppBarAction: ManageIntent
    data class OnSearchQueryChange(val query: String): ManageIntent
    object OnSearchCloseButtonClick: ManageIntent
    data class OnSelectCategoryChip(val index: Int): ManageIntent
}
