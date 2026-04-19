package com.project.ingredient.usecase.recipe

import com.project.common_core.filterWith
import com.project.ingredient.repository.RecipeRepository
import com.project.model.recipe.RecipeCategory
import com.project.model.recipe.RecipeListItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecipeListUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(
        categoryFlow: Flow<RecipeCategory?>,
        queryFlow: Flow<String>,
    ): Flow<List<RecipeListItem>> {

        return recipeRepository.getAllRecipe()
            .filterWith(
                categoryFlow = categoryFlow,
                queryFlow = queryFlow,
                getCategory = { it.category },
                getName = { it.name }
            )
    }
}
