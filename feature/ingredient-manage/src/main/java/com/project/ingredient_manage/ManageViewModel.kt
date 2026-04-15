package com.project.ingredient_manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.ingredient.usecase.manage.DeleteHoldIngredientUseCase
import com.project.ingredient.usecase.manage.GetAllHoldIngredientUseCase
import com.project.ingredient_manage.contract.ManageEffect
import com.project.ingredient_manage.contract.ManageIntent
import com.project.ingredient_manage.contract.ManageState
import com.project.model.ingredient.Ingredient
import com.project.model.ingredient.IngredientCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toImmutableMap
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
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

    private val _items = MutableStateFlow<ImmutableList<Ingredient>>(persistentListOf())
    private val items = _items.asStateFlow()

    private val _query = MutableStateFlow("")
    private val query: StateFlow<String> = _query.asStateFlow()

    private val _selectedCategory = MutableStateFlow<IngredientCategory?>(null)
    private val selectedCategory: StateFlow<IngredientCategory?> = _selectedCategory.asStateFlow()

    init {
        getAllHoldIngredientUseCase.invoke()
            .flatMapLatest { ingredients ->
                selectedCategory
                    .combine(
                        query
                            .onEach { q ->
                                intent { reduce { state.copy(query = q) } }
                            }
                            .debounce(300L)
                    ) { category, q ->
                        ingredients
                            .filter { ingredient ->
                                (category == null || ingredient.category == category) &&
                                        ingredient.name.contains(q)
                            }
                    }
            }
            .onEach { filterIngredients ->
                _items.value = filterIngredients.toImmutableList()
            }
            .launchIn(viewModelScope)

        items
            .onEach {
                intent {
                    reduce { state.copy(ingredientItems = it) }
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

            is ManageIntent.OnSearchQueryChange -> {
                _query.value = intent.query
            }

            is ManageIntent.OnSearchCloseButtonClick -> {
                _query.value = ""
            }

            is ManageIntent.OnSelectCategoryChip -> intent {
                reduce { state.copy(selectedCategoryIndex = intent.index) }
                _selectedCategory.value =
                    if (intent.index == 0) null else IngredientCategory.entries[intent.index - 1]
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
