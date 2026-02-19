package com.project.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.home.contract.HomeEffect
import com.project.home.contract.HomeIntent
import com.project.home.contract.HomeState
import com.project.ingredient.usecase.home.GetCurrentIngredientCount
import com.project.ingredient.usecase.home.GetExpirationDateSoonCount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCurrentIngredientCount: GetCurrentIngredientCount,
    private val getExpirationDateSoonCount: GetExpirationDateSoonCount
) : ContainerHost<HomeState, HomeEffect>, ViewModel() {
    override val container = container<HomeState, HomeEffect>(HomeState())

    private val ingredientCount: StateFlow<Int> = getCurrentIngredientCount.invoke()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = 0
        )
    private val expiresSoonCount: StateFlow<Int> = getExpirationDateSoonCount.invoke()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = 0
        )

    init {
        ingredientCount
            .onEach {
                intent { reduce { state.copy(ingredientCount = it) } }
            }
            .launchIn(viewModelScope)

        expiresSoonCount
            .onEach {
                intent { reduce { state.copy(expiresSoonCount = it) } }
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
