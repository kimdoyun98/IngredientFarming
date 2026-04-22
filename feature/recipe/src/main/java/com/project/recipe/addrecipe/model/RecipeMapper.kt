package com.project.recipe.addrecipe.model

import com.project.model.recipe.Recipe
import com.project.model.recipe.RecipeIngredient
import com.project.model.recipe.RecipeStep
import com.project.recipe.addrecipe.contract.AddRecipeState

fun AddRecipeState.asRecipe(path: String?): Recipe {
    val ingredients = ingredients.sortedBy { it.id }
    val steps = recipeSteps.sortedBy { it.id }


    return Recipe(
        id = 0,
        name = name,
        imagePath = path,
        category = selectedCategory!!,
        minute = time.toInt(),
        people = people.toInt(),
        ingredients = ingredients.map { ingredient ->
            ingredient.asRecipeIngredient()
        },
        recipeSteps = steps.mapIndexed { idx, step ->
            step.asRecipeSteps(idx+1)
        }
    )
}

fun IngredientUiModel.asRecipeIngredient() = RecipeIngredient(
    id = 0,
    ingredientId = -1,
    name = name,
    count = amount.toDouble(),
    unit = unit
)

fun RecipeStepUiModel.asRecipeSteps(number: Int) = RecipeStep(
    id = 0,
    number = number,
    description = description
)
