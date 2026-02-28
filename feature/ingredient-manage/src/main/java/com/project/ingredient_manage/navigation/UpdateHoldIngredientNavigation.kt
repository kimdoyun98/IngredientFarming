package com.project.ingredient_manage.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.ingredient_manage.update.UpdateHoldIngredientScreen
import com.project.ingredient_manage.update.UpdateHoldIngredientViewModel
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

fun NavGraphBuilder.updateHoldIngredientGraph(
    navigator: IngredientFarmingNavigator,
) {
    composable<IngredientRoute.UpdateHoldIngredient> {
        val updateViewModel: UpdateHoldIngredientViewModel = hiltViewModel()
        val updateState by updateViewModel.collectAsState()

        updateViewModel.collectSideEffect { effect ->
            when (effect) {

            }
        }

        UpdateHoldIngredientScreen(
            updateState = updateState,
            onIntent = updateViewModel::onIntent,
        )
    }
}
