package com.project.ingredient_manage.defaultingredient.contract

import com.project.model.ingredient.DefaultIngredient
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore

sealed interface DefaultIngredientIntent {
    object OnTopAppBarNavigationClick: DefaultIngredientIntent
    data class SearchQueryChange(val query: String): DefaultIngredientIntent
    object OnSearchCloseButtonClick: DefaultIngredientIntent
    data class SelectCategoryChip(val category: IngredientCategory?): DefaultIngredientIntent
    data class OnDefaultIngredientItemClick(val ingredient: DefaultIngredient): DefaultIngredientIntent
    data class ShowDialogStateChange(val state: Boolean): DefaultIngredientIntent
    data class OnDialogCategorySelect(val category: IngredientCategory): DefaultIngredientIntent
    data class OnDialogStoreSelect(val store: IngredientStore): DefaultIngredientIntent
    object OnDialogDismissButtonClick: DefaultIngredientIntent
    object OnDialogSaveButtonClick: DefaultIngredientIntent
}
