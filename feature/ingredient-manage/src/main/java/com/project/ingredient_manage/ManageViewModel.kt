package com.project.ingredient_manage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.ingredient.usecase.SearchIngredientUseCase
import com.project.ingredient_manage.contract.ManageEffect
import com.project.ingredient_manage.contract.ManageIntent
import com.project.ingredient_manage.contract.ManageState
import com.project.model.barcode.IngredientCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val searchIngredientUseCase: SearchIngredientUseCase,
) : ContainerHost<ManageState, ManageEffect>, ViewModel() {
    override val container = container<ManageState, ManageEffect>(ManageState())

    private val _query = MutableStateFlow("")
    private val query: StateFlow<String> = _query.asStateFlow()

    private val _selectedCategory = MutableStateFlow<IngredientCategory?>(null)
    private val selectedCategory: StateFlow<IngredientCategory?> = _selectedCategory.asStateFlow()

    init {
        selectedCategory
            .flatMapLatest { category ->
                query
                    .debounce(500L)
                    .onEach { q ->
                        val items = searchIngredientUseCase.invoke(q, category)
                        intent {
                            reduce { state.copy(ingredientItems = items) }
                        }
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
                _query.value = intent.query
            }

            is ManageIntent.OnSearchCloseButtonClick -> intent {
                reduce { state.copy(query = "") }
                _query.value = ""
            }

            is ManageIntent.OnSelectCategoryChip -> intent {
                reduce { state.copy(selectedCategoryIndex = intent.index) }
                _selectedCategory.value =
                    if (intent.index == 0) null else IngredientCategory.entries[intent.index - 1]
            }
        }
    }
}
