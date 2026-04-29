package com.project.recipe.recipelist.contract

import androidx.compose.runtime.Stable
import com.project.model.recipe.RecipeCategory
import com.project.model.recipe.RecipeListItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
data class RecipeState(
    val query: String = "",
    val selectedCategory: RecipeCategory? = null,
    val recipeList: ImmutableList<RecipeListItem> = persistentListOf()
)
