package com.project.recipe.recipinfo

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.project.ingredient.usecase.recipe.CheckRecipeIngredientsAvailabilityUseCase
import com.project.ingredient.usecase.recipe.GetRecipeInfoUseCase
import com.project.navigation.IngredientRoute
import com.project.recipe.recipinfo.contract.RecipeInfoEffect
import com.project.recipe.recipinfo.contract.RecipeInfoIntent
import com.project.recipe.recipinfo.contract.RecipeInfoState
import com.project.recipe.recipinfo.model.asUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RecipeInfoViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getRecipeInfoUseCase: GetRecipeInfoUseCase,
    private val checkRecipeIngredientsAvailabilityUseCase: CheckRecipeIngredientsAvailabilityUseCase,
) : ContainerHost<RecipeInfoState, RecipeInfoEffect>, ViewModel() {
    private val route: IngredientRoute.RecipeInfo = savedStateHandle.toRoute()
    override val container = container<RecipeInfoState, RecipeInfoEffect>(RecipeInfoState())

    init {
        intent {
            val recipe = getRecipeInfoUseCase.invoke(route.recipeId)
            val ingredientAvailability =
                checkRecipeIngredientsAvailabilityUseCase.invoke(recipe.ingredients)

            reduce {
                state.copy(
                    name = recipe.name,
                    imagePath = recipe.imagePath,
                    category = recipe.category,
                    minute = recipe.minute,
                    people = recipe.people,
                    ingredients =
                        recipe.ingredients
                            .map { ingredient ->
                                ingredient.asUiModel(
                                    ingredientAvailability
                                        .find {
                                            it.ingredientId == ingredient.ingredientId
                                        }?.isAvailability ?: false
                                )
                            }
                            .toImmutableList(),
                    recipeSteps = recipe.recipeSteps.toImmutableList()
                )
            }
        }
    }

    fun onIntent(intent: RecipeInfoIntent) {
        when(intent){
            is RecipeInfoIntent.OnTopAppBarNavigationClick -> intent {
                postSideEffect(RecipeInfoEffect.NavigateToBack)
            }
        }
    }
}
