package com.project.recipe.recipelist.model

import androidx.compose.runtime.Stable
import com.project.model.recipe.RecipeCategory
import com.project.model.recipe.RecipeListItem
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Stable
data class RecipeListItemUiModel(
    val id: Int = 0,
    val image: String? = null,
    val name: String = "",
    val category: RecipeCategory = RecipeCategory.KOREAN_FOOD,
    val minute: Int = 30,
    val people: Int = 2,
    val ingredientsAvailable: ImmutableList<Boolean> = persistentListOf(),
)

internal fun RecipeListItem.asUiModel(ingredientsAvailable: ImmutableList<Boolean>) =
    RecipeListItemUiModel(
        id = id,
        image = image,
        name = name,
        category = category,
        minute = minute,
        people = people,
        ingredientsAvailable = ingredientsAvailable
    )
