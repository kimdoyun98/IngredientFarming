package com.project.ingredient_manage.defaultingredient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.project.ingredient.usecase.manage.GetDefaultIngredientsUseCase
import com.project.ingredient_manage.defaultingredient.contract.DefaultIngredientEffect
import com.project.ingredient_manage.defaultingredient.contract.DefaultIngredientIntent
import com.project.ingredient_manage.defaultingredient.contract.DefaultIngredientState
import com.project.model.ingredient.IngredientFilter
import dagger.hilt.android.lifecycle.HiltViewModel
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
class DefaultIngredientManageViewModel @Inject constructor(
    private val getDefaultIngredientsUseCase: GetDefaultIngredientsUseCase,
) : ContainerHost<DefaultIngredientState, DefaultIngredientEffect>, ViewModel() {
    override val container =
        container<DefaultIngredientState, DefaultIngredientEffect>(DefaultIngredientState())

    private val queryFlow = container.stateFlow.map { it.query }
    private val categoryFlow = container.stateFlow.map { it.selectedCategory }

    @OptIn(FlowPreview::class)
    private val filterFlow: Flow<IngredientFilter> =
        combine(queryFlow, categoryFlow) { query, category ->
            IngredientFilter(
                query = query,
                category = category
            )
        }
            .debounce(300)
            .distinctUntilChanged()

    @OptIn(FlowPreview::class)
    val ingredients = filterFlow
        .debounce(300L)
        .distinctUntilChanged()
        .flatMapLatest { filter ->
            getDefaultIngredientsUseCase.invoke(filter)
        }
        .cachedIn(viewModelScope)

    private var selectedIngredientId: Int? = null

    fun onIntent(intent: DefaultIngredientIntent) {
        when (intent) {
            is DefaultIngredientIntent.OnTopAppBarNavigationClick -> intent {
                postSideEffect(DefaultIngredientEffect.NavigateToBack)
            }

            is DefaultIngredientIntent.SearchQueryChange -> intent {
                reduce { state.copy(query = intent.query) }
            }

            is DefaultIngredientIntent.OnSearchCloseButtonClick -> intent {
                reduce { state.copy(query = "") }
            }

            is DefaultIngredientIntent.SelectCategoryChip -> intent {
                reduce { state.copy(selectedCategory = intent.category) }
            }

            is DefaultIngredientIntent.OnDefaultIngredientItemClick -> intent {
                selectedIngredientId = intent.ingredient.id
                reduce {
                    state.copy(
                        showDialog = true,
                        dialogIngredientName = intent.ingredient.name,
                        selectedDialogCategory = null,
                        selectedDialogStore = null
                    )
                }
            }

            is DefaultIngredientIntent.ShowDialogStateChange -> intent {
                reduce { state.copy(showDialog = intent.state) }
            }

            is DefaultIngredientIntent.OnDialogStoreSelect -> intent {
                reduce { state.copy(selectedDialogStore = intent.store) }
            }

            is DefaultIngredientIntent.OnDialogCategorySelect -> intent {
                reduce { state.copy(selectedDialogCategory = intent.category) }
            }

            is DefaultIngredientIntent.OnDialogDismissButtonClick -> {
                resetDialogState()
            }

            is DefaultIngredientIntent.OnDialogSaveButtonClick -> intent {
                if (state.selectedDialogCategory == null || state.selectedDialogStore == null) return@intent
                //TODO Update

                resetDialogState()
            }
        }
    }

    private fun resetDialogState() = intent {
        reduce {
            state.copy(
                showDialog = false,
                dialogIngredientName = "",
                selectedDialogCategory = null,
                selectedDialogStore = null
            )
        }
    }
}
