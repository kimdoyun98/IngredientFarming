package com.project.ingredient_manage.defaultingredient.contract

import com.project.model.ingredient.IngredientCategory

sealed interface DefaultIngredientIntent {
    object OnTopAppBarNavigationClick: DefaultIngredientIntent
    data class SearchQueryChange(val query: String): DefaultIngredientIntent
    object OnSearchCloseButtonClick: DefaultIngredientIntent
    data class SelectCategoryChip(val category: IngredientCategory?): DefaultIngredientIntent
    data class OnDefaultIngredientItemClick(val id: Int): DefaultIngredientIntent
}
