package com.project.recipe.recipelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.project.ingredient.usecase.recipe.CheckRecipeIngredientsAvailabilityUseCase
import com.project.ingredient.usecase.recipe.GetAllHoldIngredientCountUseCase
import com.project.ingredient.usecase.recipe.GetRecipeListUseCase
import com.project.model.recipe.RecipeFilter
import com.project.model.recipe.asCheckRecipeAvailability
import com.project.recipe.recipelist.contract.RecipeEffect
import com.project.recipe.recipelist.contract.RecipeIntent
import com.project.recipe.recipelist.contract.RecipeState
import com.project.recipe.recipelist.model.asUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val getRecipeListUseCase: GetRecipeListUseCase,
    private val getAllHoldIngredientCountUseCase: GetAllHoldIngredientCountUseCase,
    private val checkRecipeIngredientsAvailabilityUseCase: CheckRecipeIngredientsAvailabilityUseCase,
) : ContainerHost<RecipeState, RecipeEffect>, ViewModel() {
    override val container = container<RecipeState, RecipeEffect>(RecipeState())

    private val queryFlow = container.stateFlow.map { it.query }
    private val categoryFlow = container.stateFlow.map { it.selectedCategory }
    @OptIn(FlowPreview::class)
    private val filterFlow: Flow<RecipeFilter> =
        combine(queryFlow, categoryFlow) { query, category ->
            RecipeFilter(
                query = query,
                category = category
            )
        }
            .debounce(300)
            .distinctUntilChanged()
    private val holdIngredientsFlow =
        getAllHoldIngredientCountUseCase.invoke()
            .map { holdIngredientCount ->
                holdIngredientCount.associateBy { it.ingredientId }
            }

    @OptIn(FlowPreview::class)
    val recipes = filterFlow
        .debounce(300L)
        .distinctUntilChanged()
        .flatMapLatest { filter ->

            val pagingFlow = getRecipeListUseCase.invoke(filter)

            pagingFlow.combine(holdIngredientsFlow) { pagingData, holdIngredientsMap ->
                pagingData.map { recipeListItem ->
                    val map = checkRecipeIngredientsAvailabilityUseCase.invoke(
                        holdIngredientCount = holdIngredientsMap,
                        recipeIngredients = recipeListItem.ingredients.map {
                            it.asCheckRecipeAvailability(
                                recipeListItem.id
                            )
                        }
                    )

                    recipeListItem.asUiModel(map.values.map { it }.toImmutableList())
                }
            }
        }
        .cachedIn(viewModelScope)

    fun onIntent(intent: RecipeIntent) {
        when (intent) {
            is RecipeIntent.OnTopAppBarNavigationClick -> intent {
                postSideEffect(RecipeEffect.NavigateToHome)
            }

            is RecipeIntent.OnTopAppBarActionClick -> intent {
                postSideEffect(RecipeEffect.NavigateToRecipeAdd)
            }

            is RecipeIntent.RecipeItemClick -> intent {
                postSideEffect(RecipeEffect.NavigateToRecipeInfo(intent.recipeId))
            }

            is RecipeIntent.SearchRecipeQueryChange -> intent {
                reduce { state.copy(query = intent.query) }
            }

            is RecipeIntent.SearchRecipeQueryReset -> intent {
                reduce { state.copy(query = "") }
            }

            is RecipeIntent.SelectCategoryChip -> intent {
                reduce { state.copy(selectedCategory = intent.category) }
            }
        }
    }
}
