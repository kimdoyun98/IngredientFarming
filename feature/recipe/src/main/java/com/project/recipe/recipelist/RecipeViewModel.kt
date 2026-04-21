package com.project.recipe.recipelist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.ingredient.usecase.recipe.GetRecipeListUseCase
import com.project.model.recipe.RecipeCategory
import com.project.recipe.recipelist.contract.RecipeEffect
import com.project.recipe.recipelist.contract.RecipeIntent
import com.project.recipe.recipelist.contract.RecipeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val getRecipeListUseCase: GetRecipeListUseCase
) : ContainerHost<RecipeState, RecipeEffect>, ViewModel() {
    override val container = container<RecipeState, RecipeEffect>(RecipeState())

    private val _query = MutableStateFlow("")
    private val query =
        _query
            .onEach {
                intent { reduce { state.copy(query = it) } }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ""
            )

    private val _category = MutableStateFlow<RecipeCategory?>(null)
    private val category = _category.asStateFlow()

    init {
        getRecipeListUseCase.invoke(
            categoryFlow = category,
            queryFlow = query,
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
                _query.value = intent.query
                reduce { state.copy(query = intent.query) }
            }

            is RecipeIntent.SearchRecipeQueryReset -> intent {
                _query.value = ""
                reduce { state.copy(query = "") }
            }

            is RecipeIntent.SelectCategoryChip -> intent {
                _category.value = intent.category
                reduce { state.copy(selectedCategory = intent.category) }
            }
        }
    }
}
