package com.project.ingredient_manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.common_core.filterWith
import com.project.ingredient.usecase.manage.DeleteHoldIngredientUseCase
import com.project.ingredient.usecase.manage.GetAllHoldIngredientUseCase
import com.project.ingredient_manage.contract.ManageEffect
import com.project.ingredient_manage.contract.ManageIntent
import com.project.ingredient_manage.contract.ManageState
import com.project.model.ingredient.IngredientCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class ManageViewModel @Inject constructor(
    private val getAllHoldIngredientUseCase: GetAllHoldIngredientUseCase,
    private val deleteHoldIngredientUseCase: DeleteHoldIngredientUseCase,
) : ContainerHost<ManageState, ManageEffect>, ViewModel() {
    override val container = container<ManageState, ManageEffect>(ManageState())

    private val query = container.stateFlow.map { it.query }
    private val selectedCategory = container.stateFlow.map {
        if (it.selectedCategoryIndex == 0) null
        else IngredientCategory.entries[it.selectedCategoryIndex - 1]
    }

    init {
        getAllHoldIngredientUseCase.invoke()
            .filterWith(
                categoryFlow = selectedCategory,
                queryFlow = query,
                getCategory = { it.category },
                getName = { it.name }
            )
            .map {
                it.sortedBy { ingredient -> ingredient.expirationDate }
            }
            .onEach { ingredients ->
                intent {
                    reduce { state.copy(ingredientItems = ingredients.toImmutableList()) }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: ManageIntent) {
        when (intent) {
            is ManageIntent.OnClickTopAppBarNavigation -> intent {
                postSideEffect(ManageEffect.NavigateToHome)
            }

            is ManageIntent.OnClickTopAppBarAction -> intent {

            }

            is ManageIntent.OnSearchQueryChange -> intent {
                reduce { state.copy(query = intent.query) }
            }

            is ManageIntent.OnSearchCloseButtonClick -> intent {
                reduce { state.copy(query = "") }
            }

            is ManageIntent.OnSelectCategoryChip -> intent {
                reduce { state.copy(selectedCategoryIndex = intent.index) }
            }

            is ManageIntent.OnClickItem -> intent {
                if (state.deleteOptionsState) {
                    val map = state.selectedItems.toMutableMap()
                    map[intent.id] = map[intent.id] == null || map[intent.id] == false

                    reduce { state.copy(selectedItems = map.toImmutableMap()) }
                } else {
                    postSideEffect(ManageEffect.NavigateToUpdateIngredient(intent.id))
                }
            }

            is ManageIntent.OnLongClickItem -> intent {
                if (state.deleteOptionsState) return@intent

                val map = state.selectedItems.toMutableMap()
                map[intent.id] = true

                reduce {
                    state.copy(
                        deleteOptionsState = true,
                        selectedItems = map.toImmutableMap()
                    )
                }
            }

            is ManageIntent.OnClickAllSelectRadioButton -> intent {
                reduce {
                    state.copy(
                        allSelectedState = !state.allSelectedState,
                        selectedItems =
                            if (state.allSelectedState) {
                                persistentMapOf()
                            } else {
                                val map = mutableMapOf<Int, Boolean>()
                                state.ingredientItems.map {
                                    map[it.id] = true
                                }
                                map.toImmutableMap()
                            }
                    )
                }
            }

            is ManageIntent.OnClickDeleteOptionsCancel -> intent {
                reduce { state.copy(deleteOptionsState = false, selectedItems = persistentMapOf()) }
            }

            is ManageIntent.OnDeleteButtonClick -> intent {
                val deleteIds = state.selectedItems.toList().filter { it.second }.map { it.first }
                deleteHoldIngredientUseCase.invoke(deleteIds)

                postSideEffect(ManageEffect.ShowSnackBar)

                onIntent(ManageIntent.OnClickDeleteOptionsCancel)
            }
        }
    }
}
