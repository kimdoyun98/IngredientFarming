package com.project.recipe.recipinfo.contract

import androidx.compose.runtime.Stable
import com.project.model.recipe.RecipeCategory
import com.project.model.recipe.RecipeStep
import com.project.recipe.recipinfo.model.RecipeIngredientUiModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
data class RecipeInfoState(
    val name: String = "",
    val imagePath: String? = null,
    val category: RecipeCategory = RecipeCategory.KOREAN_FOOD,
    val minute: Int = 30,
    val people: Int = 2,
    val ingredients: ImmutableList<RecipeIngredientUiModel> = persistentListOf(),
    val recipeSteps: ImmutableList<RecipeStep> = persistentListOf()
)
