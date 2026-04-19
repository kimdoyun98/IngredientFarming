package com.project.recipe

import com.project.database.model.recipe.RecipeEntity
import com.project.database.model.recipe.RecipeIngredientEntity
import com.project.database.model.recipe.RecipeStepEntity
import com.project.database.relation.RecipeWithIngredients
import com.project.model.recipe.Recipe
import com.project.model.recipe.RecipeIngredient
import com.project.model.recipe.RecipeListItem
import com.project.model.recipe.RecipeStep

internal fun Recipe.asRecipeEntity() =
    RecipeEntity(
        name = name,
        imageUri = imageUri,
        category = category,
        minute = minute,
        people = people
    )

internal fun RecipeIngredient.asRecipeIngredientEntity(recipeId: Int) =
    RecipeIngredientEntity(
        recipeId = recipeId,
        name = name,
        count = count,
        unit = unit
    )

internal fun RecipeStep.asRecipeStepEntity(recipeId: Int) =
    RecipeStepEntity(
        recipeId = recipeId,
        number = number,
        description = description
    )

internal fun RecipeWithIngredients.asExternalModel(): RecipeListItem {
    return RecipeListItem(
        id = recipe.id,
        photo = recipe.imageUri,
        name = recipe.name,
        category = recipe.category,
        minute = recipe.minute,
        people = recipe.people,
        ingredients = ingredients.map {
            RecipeIngredient(
                id = it.id,
                name = it.name,
                count = it.count,
                unit = it.unit
            )
        }
    )
}
