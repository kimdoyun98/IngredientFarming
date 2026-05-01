package com.project.recipe.recipinfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.project.ingredient.usecase.recipe.CheckRecipeIngredientsAvailabilityUseCase
import com.project.ingredient.usecase.recipe.GetAllHoldIngredientCountUseCase
import com.project.ingredient.usecase.recipe.GetRecipeInfoUseCase
import com.project.ingredient.usecase.recipe.SaveRequireIngredientsToCartUseCase
import com.project.model.recipe.asCheckRecipeAvailability
import com.project.navigation.IngredientRoute
import com.project.recipe.recipinfo.contract.RecipeInfoEffect
import com.project.recipe.recipinfo.contract.RecipeInfoIntent
import com.project.recipe.recipinfo.contract.RecipeInfoState
import com.project.recipe.recipinfo.model.asRecipeIngredient
import com.project.recipe.recipinfo.model.asUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RecipeInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRecipeInfoUseCase: GetRecipeInfoUseCase,
    private val getAllHoldIngredientCountUseCase: GetAllHoldIngredientCountUseCase,
    private val checkRecipeIngredientsAvailabilityUseCase: CheckRecipeIngredientsAvailabilityUseCase,
    private val saveRequireIngredientsToCartUseCase: SaveRequireIngredientsToCartUseCase,
) : ContainerHost<RecipeInfoState, RecipeInfoEffect>, ViewModel() {
    private val route: IngredientRoute.RecipeInfo = savedStateHandle.toRoute()
    override val container = container<RecipeInfoState, RecipeInfoEffect>(RecipeInfoState())
    private val holdIngredientsFlow =
        getAllHoldIngredientCountUseCase.invoke()
            .map { holdIngredientCount ->
                holdIngredientCount.associateBy { it.ingredientId }
            }

    init {
        intent {
            val recipe = getRecipeInfoUseCase.invoke(route.recipeId)

            val map = checkRecipeIngredientsAvailabilityUseCase.invoke(
                holdIngredientCount = holdIngredientsFlow.first(),
                recipeIngredients = recipe.ingredients.map { it.asCheckRecipeAvailability(recipe.id) }
            )

            reduce {
                state.copy(
                    name = recipe.name,
                    imagePath = recipe.imagePath,
                    category = recipe.category,
                    minute = recipe.minute,
                    people = recipe.people,
                    ingredients =
                        recipe.ingredients.map { ingredient ->
                            ingredient.asUiModel(
                                map[ingredient.ingredientId] ?: false
                            )
                        }.toImmutableList(),
                    recipeSteps = recipe.recipeSteps.toImmutableList()
                )
            }
        }
    }

    fun onIntent(intent: RecipeInfoIntent) {
        when (intent) {
            is RecipeInfoIntent.OnTopAppBarNavigationClick -> intent {
                postSideEffect(RecipeInfoEffect.NavigateToBack)
            }

            is RecipeInfoIntent.OnAddRequireIngredientButtonClick -> intent {
                val requireIngredients = state.ingredients.filter { !it.isAvailable }

                saveRequireIngredientsToCartUseCase.invoke(
                    requireIngredients.map { it.asRecipeIngredient() }
                )
            }
        }
    }
}
