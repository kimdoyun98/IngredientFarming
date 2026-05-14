package com.project.ingredient.usecase.manage

import androidx.paging.PagingData
import com.project.ingredient.repository.IngredientRepository
import com.project.model.ingredient.DefaultIngredient
import com.project.model.ingredient.IngredientFilter
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDefaultIngredientsUseCase @Inject constructor(
    private val ingredientRepository: IngredientRepository,
) {
    operator fun invoke(filter: IngredientFilter): Flow<PagingData<DefaultIngredient>> {
        return ingredientRepository.getDefaultIngredients(filter)
    }
}
