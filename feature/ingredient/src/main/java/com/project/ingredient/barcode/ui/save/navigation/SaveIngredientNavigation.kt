package com.project.ingredient.barcode.ui.save.navigation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.project.ingredient.barcode.contract.save.SaveIngredientEffect
import com.project.ingredient.barcode.ui.save.SaveIngredientScreen
import com.project.ingredient.barcode.ui.save.SaveIngredientViewModel
import com.project.model.barcode.Ingredient
import com.project.model.barcode.getIndexToIngredientCategory
import com.project.model.barcode.getIndexToIngredientStore
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import java.time.LocalDate

fun NavGraphBuilder.saveIngredientGraph(
    navigator: IngredientFarmingNavigator,
) {
    composable<IngredientRoute.SaveIngredient> { navBackStackEntry ->
        val newIngredient = navBackStackEntry.toRoute<IngredientRoute.SaveIngredient>()
        val saveIngredientNavBackStackEntry = remember(navBackStackEntry) {
            navigator.navController.getBackStackEntry(newIngredient)
        }
        val saveIngredientViewModel: SaveIngredientViewModel =
            hiltViewModel(saveIngredientNavBackStackEntry)
        val saveIngredientState by saveIngredientViewModel.collectAsState()

        saveIngredientViewModel.collectSideEffect { effect ->
            when (effect) {
                SaveIngredientEffect.SaveIngredient -> {

                }

                SaveIngredientEffect.NavigateToDirectInputScreen -> {
                    navigator.navigateToDirectInput()
                }

                SaveIngredientEffect.NavigateToBarcodeScannerScreen -> {

                }
            }
        }

        SaveIngredientScreen(
            state = { saveIngredientState },
            onIntent = saveIngredientViewModel::onIntent,
        )

        LaunchedEffect(Unit) {
            saveIngredientViewModel.addNewIngredient(
                Ingredient(
                    name = newIngredient.name,
                    count = 1,
                    category = getIndexToIngredientCategory(newIngredient.categorySelected),
                    enterDate = LocalDate.now(),
                    expirationDate = if (newIngredient.expirationDate.isBlank()) null else LocalDate.parse(
                        newIngredient.expirationDate
                    ),
                    store = getIndexToIngredientStore(newIngredient.storeSelected),
                )
            )
        }
    }
}
