package com.project.home

import androidx.lifecycle.ViewModel
import com.project.home.contract.HomeEffect
import com.project.home.contract.HomeIntent
import com.project.home.contract.HomeState
import com.project.home.util.toCountValue
import com.project.ingredient.usecase.home.GetCurrentIngredientCountUseCase
import com.project.ingredient.usecase.home.GetExpirationDateSoonCountUseCase
import com.project.ingredient.usecase.home.GetExpirationDateSoonIngredientUseCase
import com.project.ingredient.usecase.home.GetRecipeCountUseCase
import com.project.ingredient.usecase.home.GetShoppingCartItemsCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentIngredientCountUseCase: GetCurrentIngredientCountUseCase,
    private val getExpirationDateSoonCountUseCase: GetExpirationDateSoonCountUseCase,
    private val getExpirationDateSoonIngredientUseCase: GetExpirationDateSoonIngredientUseCase,
    private val getRecipeCountUseCase: GetRecipeCountUseCase,
    private val getShoppingCartItemsCountUseCase: GetShoppingCartItemsCountUseCase,
) : ContainerHost<HomeState, HomeEffect>, ViewModel() {
    override val container = container<HomeState, HomeEffect>(HomeState())

    init {
        intent {
            getCurrentIngredientCountUseCase.invoke()
                .collectLatest {
                    reduce { state.copy(ingredientCount = it) }
                }
        }

        intent {
            getExpirationDateSoonCountUseCase.invoke()
                .collectLatest {
                    reduce { state.copy(expiresSoonCount = it) }
                }
        }

        intent {
            getExpirationDateSoonIngredientUseCase.invoke()
                .collectLatest {
                    reduce { state.copy(expirationDateSoonItems = it.toImmutableList()) }
                }
        }

        intent {
            getRecipeCountUseCase.invoke()
                .collectLatest {
                    reduce { state.copy(recipeCount = it) }
                }
        }

        intent {
            getShoppingCartItemsCountUseCase.invoke()
                .map {
                    it.toCountValue()
                }
                .collectLatest {
                    reduce { state.copy(shoppingCartItemsCountValue = it) }
                }
        }
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

            HomeIntent.BackPress -> intent {
                postSideEffect(HomeEffect.AppFinish)
            }
        }
    }
}
