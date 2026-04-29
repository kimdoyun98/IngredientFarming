package com.project.recipe.recipelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.common_core.filterWith
import com.project.ingredient.usecase.recipe.GetRecipeListUseCase
import com.project.recipe.recipelist.contract.RecipeEffect
import com.project.recipe.recipelist.contract.RecipeIntent
import com.project.recipe.recipelist.contract.RecipeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val getRecipeListUseCase: GetRecipeListUseCase
) : ContainerHost<RecipeState, RecipeEffect>, ViewModel() {
    override val container = container<RecipeState, RecipeEffect>(RecipeState())

    private val query = container.stateFlow.map { it.query }
    private val category = container.stateFlow.map { it.selectedCategory }

    init {
        getRecipeListUseCase.invoke()
            .filterWith(
                categoryFlow = category,
                queryFlow = query,
                getCategory = { it.category },
                getName = { it.name }
            )
            .onEach {
                intent { reduce { state.copy(recipeList = it.toImmutableList()) } }
            }
            .launchIn(viewModelScope)
    }

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
