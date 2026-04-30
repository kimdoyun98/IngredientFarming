package com.project.ingredient.usecase.recipe

import androidx.paging.PagingData
import com.project.ingredient.repository.RecipeRepository
import com.project.model.recipe.RecipeFilter
import com.project.model.recipe.RecipeListItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRecipeListUseCase @Inject constructor(
    private val recipeRepository: RecipeRepository
) {
    operator fun invoke(filter: RecipeFilter): Flow<PagingData<RecipeListItem>> {

        return recipeRepository.getPagedRecipes(filter)
    }
}
