package com.project.shopping_cart.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute
import com.project.shopping_cart.ShoppingCartScreen
import com.project.shopping_cart.ShoppingCartViewModel
import com.project.shopping_cart.contract.ShoppingCartEffect
import com.project.shopping_cart.contract.ShoppingCartState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

fun NavGraphBuilder.shoppingCartGraph(
    navigator: IngredientFarmingNavigator,
) {
    composable<IngredientRoute.ShoppingCart> {
        val shoppingCartViewModel: ShoppingCartViewModel = hiltViewModel()
        val shoppingCartState by shoppingCartViewModel.collectAsState()

        shoppingCartViewModel.collectSideEffect { effect ->
            when(effect) {
                ShoppingCartEffect.PopBackStack -> {
                    navigator.navController.popBackStack()
                }
            }
        }

        ShoppingCartScreen(
            shoppingCartState = shoppingCartState,
            onIntent =  shoppingCartViewModel::onIntent
        )
    }
}
