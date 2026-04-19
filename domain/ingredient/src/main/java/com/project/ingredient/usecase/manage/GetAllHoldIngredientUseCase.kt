package com.project.ingredient.usecase.manage

import com.project.common_core.filterWith
import com.project.ingredient.repository.HoldIngredientRepository
import com.project.model.ingredient.Ingredient
import com.project.model.ingredient.IngredientCategory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllHoldIngredientUseCase @Inject constructor(
    private val holdIngredientRepository: HoldIngredientRepository,
) {
    operator fun invoke(
        categoryFlow: Flow<IngredientCategory?>,
        queryFlow: Flow<String>
    ): Flow<List<Ingredient>> {
        return holdIngredientRepository.getAllHoldIngredient()
            .filterWith(
                categoryFlow = categoryFlow,
                queryFlow = queryFlow,
                getCategory = { it.category },
                getName = { it.name }
            )
            .map {
                it.sortedBy { ingredient -> ingredient.expirationDate }
            }
    }
}
