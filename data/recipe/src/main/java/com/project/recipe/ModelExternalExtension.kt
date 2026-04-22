package com.project.recipe

import com.project.database.model.recipe.RecipeIngredientEntity
import com.project.database.model.recipe.RecipeStepEntity
import com.project.database.relation.RecipeInfoRelation
import com.project.database.relation.RecipeWithIngredients
import com.project.model.recipe.Recipe
import com.project.model.recipe.RecipeIngredient
import com.project.model.recipe.RecipeListItem
import com.project.model.recipe.RecipeStep

internal fun RecipeWithIngredients.asExternalModel(): RecipeListItem {
    return RecipeListItem(
        id = recipe.id,
        image = recipe.image,
        name = recipe.name,
        category = recipe.category,
        minute = recipe.minute,
        people = recipe.people,
        ingredients = ingredients.map { it.ingredientId }
    )
}

internal fun RecipeIngredientEntity.asExternalModel(name: String) =
    RecipeIngredient(
        id = id,
        ingredientId = ingredientId,
        name = name,
        count = count,
        unit = unit
    )

internal fun RecipeStepEntity.asExternalModel() =
    RecipeStep(
        id = id,
        number = number,
        description = description
    )

internal fun RecipeInfoRelation.asExternalModel(): Recipe =
    Recipe(
        id = recipe.id,
        name = recipe.name,
        imagePath = recipe.image,
        category = recipe.category,
        minute = recipe.minute,
        people = recipe.people,
        ingredients = ingredients.map {
            val name = it.ingredient.name
            it.recipeIngredient.asExternalModel(name)
        },
        recipeSteps = steps.map { it.asExternalModel() }
    )
