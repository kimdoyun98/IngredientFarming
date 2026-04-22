package com.project.database.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.project.database.model.IngredientEntity
import com.project.database.model.recipe.RecipeEntity
import com.project.database.model.recipe.RecipeIngredientEntity
import com.project.database.model.recipe.RecipeStepEntity

data class RecipeInfoRelation (
    @Embedded
    val recipe: RecipeEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId"
    )
    val steps: List<RecipeStepEntity>,

    @Relation(
        parentColumn = "id",
        entityColumn = "recipeId",
        entity = RecipeIngredientEntity::class
    )
    val ingredients: List<RecipeIngredientWithDetail>
)

data class IngredientNameOnly(
    val id: Int,
    val name: String
)

data class RecipeIngredientWithDetail(
    @Embedded
    val recipeIngredient: RecipeIngredientEntity,

    @Relation(
        parentColumn = "ingredientId",
        entityColumn = "id",
        entity = IngredientEntity::class
    )
    val ingredient: IngredientNameOnly
)
