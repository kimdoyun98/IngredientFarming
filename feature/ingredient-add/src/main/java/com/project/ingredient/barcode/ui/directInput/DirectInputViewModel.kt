package com.project.ingredient.barcode.ui.directInput

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.ingredient.barcode.contract.directInput.DirectInputEffect
import com.project.ingredient.barcode.contract.directInput.DirectInputIntent
import com.project.ingredient.barcode.contract.directInput.DirectInputState
import com.project.ingredient.usecase.GetIngredientUseCase
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore
import com.project.model.ingredient.getIndexByIngredientCategory
import com.project.model.ingredient.getIndexByIngredientStore
import com.project.ui.util.RegexDate.regexDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class DirectInputViewModel @Inject constructor(
    private val getIngredientUseCase: GetIngredientUseCase
) : ContainerHost<DirectInputState, DirectInputEffect>, ViewModel() {
    override val container = container<DirectInputState, DirectInputEffect>(DirectInputState())

    private val _nameQuery = MutableStateFlow("")
    private val nameQuery = _nameQuery.asStateFlow()

    init {
        nameQuery
            .debounce(500L)
            .onEach {
                getIngredientUseCase.invoke(it)?.let { info ->
                    changeFilters(
                        categorySelected = getIndexByIngredientCategory(info.category),
                        storeSelected = getIndexByIngredientStore(info.store)
                    )
                } ?: changeFilters()
            }
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: DirectInputIntent) {
        when (intent) {
            is DirectInputIntent.TopAppBarNavigationClick -> intent {
                postSideEffect(DirectInputEffect.NavigateToBack)
            }

            is DirectInputIntent.StoreFilterChipSelect -> intent {
                reduce {
                    state.copy(
                        storeSelected = if (state.storeSelected == intent.selected) null else intent.selected
                    )
                }
            }

            is DirectInputIntent.CategoryFilterChipSelect -> intent {
                reduce {
                    state.copy(
                        categorySelected = if (state.categorySelected == intent.selected) null else intent.selected
                    )
                }
            }

            is DirectInputIntent.NextButtonClick -> intent {
                postSideEffect(DirectInputEffect.NavigateToSaveIngredient)
            }

            is DirectInputIntent.NameInputChange -> intent {
                _nameQuery.value = intent.name
                reduce {
                    state.copy(
                        name = intent.name
                    )
                }
            }

            is DirectInputIntent.CountInputChange -> intent {
                reduce {
                    state.copy(count = intent.count)
                }
            }

            is DirectInputIntent.ExpirationDateInputChange -> intent {
                reduce {
                    state.copy(
                        expirationDate = regexDate(intent.expirationDate)
                    )
                }
            }
        }
    }

    private fun changeFilters(
        categorySelected: Int? = null,
        storeSelected: Int? = null
    ) = intent {
        reduce {
            state.copy(categorySelected = categorySelected, storeSelected = storeSelected)
        }
    }
}
