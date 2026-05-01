package com.project.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.home.contract.HomeEffect
import com.project.home.contract.HomeIntent
import com.project.home.contract.HomeState
import com.project.ingredient.usecase.home.GetCurrentIngredientCountUseCase
import com.project.ingredient.usecase.home.GetExpirationDateSoonCountUseCase
import com.project.ingredient.usecase.home.GetExpirationDateSoonIngredientUseCase
import com.project.ingredient.usecase.home.GetRecipeCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentIngredientCountUseCase: GetCurrentIngredientCountUseCase,
    private val getExpirationDateSoonCountUseCase: GetExpirationDateSoonCountUseCase,
    private val getExpirationDateSoonIngredientUseCase: GetExpirationDateSoonIngredientUseCase,
    private val getRecipeCountUseCase: GetRecipeCountUseCase,
) : ContainerHost<HomeState, HomeEffect>, ViewModel() {
    override val container = container<HomeState, HomeEffect>(HomeState())

    init {
        getCurrentIngredientCountUseCase.invoke()
            .onEach {
                intent { reduce { state.copy(ingredientCount = it) } }
            }
            .launchIn(viewModelScope)

        getExpirationDateSoonCountUseCase.invoke()
            .onEach {
                intent { reduce { state.copy(expiresSoonCount = it) } }
            }
            .launchIn(viewModelScope)

        getExpirationDateSoonIngredientUseCase.invoke()
            .onEach {
                intent {
                    reduce {
                        state.copy(expirationDateSoonItems = it.toImmutableList())
                    }
                }
            }
            .launchIn(viewModelScope)

        getRecipeCountUseCase.invoke()
            .onEach {
                intent { reduce { state.copy(recipeCount = it) } }
            }
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            HomeIntent.OnManageButtonClick -> intent {
                postSideEffect(HomeEffect.NavigateToManage)
            }

            HomeIntent.OnAddButtonClick -> intent {
                reduce { state.copy(addStatus = true) }
            }

            HomeIntent.OnDismissRequestToAdd -> intent {
                reduce { state.copy(addStatus = false) }
            }

            HomeIntent.OnDirectInputButtonClick -> intent {
                reduce { state.copy(addStatus = false) }
                postSideEffect(HomeEffect.NavigateToDirectInput)
            }

            HomeIntent.OnBarcodeScannerButtonClick -> intent {
                reduce { state.copy(addStatus = false) }
                postSideEffect(HomeEffect.NavigateToBarcodeScanner)
            }

            HomeIntent.OnRecipeButtonClick -> intent {
                postSideEffect(HomeEffect.NavigateToRecipe)
            }

            HomeIntent.OnShoppingCartButtonClick -> intent {
                postSideEffect(HomeEffect.NavigateToShoppingCart)
            }
        }
    }
}
