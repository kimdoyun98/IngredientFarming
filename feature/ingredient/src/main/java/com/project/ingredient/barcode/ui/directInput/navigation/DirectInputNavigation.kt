package com.project.ingredient.barcode.ui.directInput.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.ingredient.barcode.contract.directInput.DirectInputEffect
import com.project.ingredient.barcode.ui.directInput.DirectInputScreen
import com.project.ingredient.barcode.ui.directInput.DirectInputViewModel
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

fun NavGraphBuilder.directInputGraph(
    navigator: IngredientFarmingNavigator,
) {
    composable<IngredientRoute.DirectInput> {
        val directInputViewModel: DirectInputViewModel = hiltViewModel()
        val directInputState by directInputViewModel.collectAsState()

        directInputViewModel.collectSideEffect { effect ->
            when (effect) {
                is DirectInputEffect.NavigateToBack -> {
                    navigator.navController.popBackStack()
                }

                is DirectInputEffect.NavigateToSaveIngredient -> {
                    navigator.navController.popBackStack()
                    navigator.navigateToSaveIngredient(
                        IngredientRoute.SaveIngredient(
                            name = directInputState.name,
                            count = directInputState.count.toInt(),
                            expirationDate = directInputState.expirationDate,
                            storeSelected = directInputState.storeSelected,
                            categorySelected = directInputState.categorySelected
                        )
                    )
                }
            }
        }

        DirectInputScreen(
            directInputState = directInputState,
            onIntent = directInputViewModel::onIntent
        )
    }
}
