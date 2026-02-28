package com.project.ingredient_manage.contract

sealed interface ManageIntent {
    object OnClickTopAppBarNavigation : ManageIntent
    object OnClickTopAppBarAction : ManageIntent
    data class OnSearchQueryChange(val query: String) : ManageIntent
    object OnSearchCloseButtonClick : ManageIntent
    data class OnSelectCategoryChip(val index: Int) : ManageIntent
    data class OnClickItem(val id: Int) : ManageIntent
    data class OnLongClickItem(val id: Int) : ManageIntent
    object OnClickAllSelectRadioButton : ManageIntent
    object OnClickDeleteOptionsCancel : ManageIntent
    object OnDeleteButtonClick : ManageIntent
}
