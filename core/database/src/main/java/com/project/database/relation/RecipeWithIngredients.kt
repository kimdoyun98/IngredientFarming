package com.project.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.project.database.model.IngredientEntity
import com.project.database.model.recipe.RecipeEntity
import com.project.database.model.recipe.RecipeIngredientEntity

data class RecipeWithIngredients(
    @Embedded val recipe: RecipeEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId",
        entity = RecipeIngredientEntity::class
    )
    val ingredients: List<RecipeIngredientWithIngredient>
)

data class RecipeIngredientWithIngredient(

    @Embedded
    val recipeIngredient: RecipeIngredientEntity,

    @Relation(
        parentColumn = "ingredientId",
        entityColumn = "id"
    )
    val ingredient: IngredientEntity
)
