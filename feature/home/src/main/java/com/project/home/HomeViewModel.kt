package com.project.home

import androidx.lifecycle.ViewModel
import com.project.home.contract.HomeEffect
import com.project.home.contract.HomeIntent
import com.project.home.contract.HomeState
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class HomeViewModel : ContainerHost<HomeState, HomeEffect>, ViewModel() {
    override val container = container<HomeState, HomeEffect>(HomeState())

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
