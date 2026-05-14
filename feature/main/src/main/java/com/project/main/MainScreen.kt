package com.project.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.project.home.navigation.homeGraph
import com.project.ingredient.barcode.ui.barcode.navigation.barcodeScannerGraph
import com.project.ingredient.barcode.ui.directInput.navigation.directInputGraph
import com.project.ingredient.barcode.ui.save.navigation.saveIngredientGraph
import com.project.ingredient_manage.defaultingredient.navigation.defaultIngredientManageGraph
import com.project.ingredient_manage.manage.navigation.manageGraph
import com.project.ingredient_manage.update.navigation.updateHoldIngredientGraph
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute
import com.project.navigation.rememberIngredientFarmingNavigator
import com.project.permission.IngredientFarmingPermission
import com.project.recipe.addrecipe.navigation.addRecipeGraph
import com.project.recipe.recipelist.navigation.recipeGraph
import com.project.recipe.recipinfo.navigation.recipeInfoGraph
import com.project.shopping_cart.navigation.shoppingCartGraph

@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    navigator: IngredientFarmingNavigator = rememberIngredientFarmingNavigator(),
    ingredientFarmingPermission: IngredientFarmingPermission,
) {
    NavHost(
        modifier = modifier,
        navController = navigator.navController,
        startDestination = IngredientRoute.Home
    ) {
        homeGraph(
            navigateToManage = navigator::navigateToManage,
            navigateToBarcodeScanner = navigator::navigateToBarcodeScanner,
            navigateToDirectInput = navigator::navigateToDirectInput,
            navigateToRecipe = navigator::navigateToRecipe,
            navigateToShoppingCart = navigator::navigateToShoppingCart,
        )

        barcodeScannerGraph(
            navigateToSaveIngredient = navigator::navigateToSaveIngredient,
            navigateToDirectInput = navigator::navigateToDirectInput,
            requestCameraPermission = ingredientFarmingPermission::launchCameraPermission
        )

        saveIngredientGraph(
            getBackStackEntry = navigator.navController::getBackStackEntry,
            navigateToHome = navigator::navigateToHome,
            navigateToDirectInput = navigator::navigateToDirectInput,
            navigateToBarcodeScanner = navigator::navigateToBarcodeScanner
        )

        directInputGraph(
            popBackStack = navigator.navController::popBackStack,
            navigateToSaveIngredient = navigator::navigateToSaveIngredient
        )

        manageGraph(
            navigateToHome = navigator::navigateToHome,
            navigateToDefaultIngredientManage = navigator::navigateToDefaultIngredientManage,
            navigateToUpdateHoldIngredient = navigator::navigateToUpdateHoldIngredient,
        )

        defaultIngredientManageGraph(
            popBackStack = navigator.navController::popBackStack
        )

        updateHoldIngredientGraph(
            popBackStack = navigator.navController::popBackStack
        )

        shoppingCartGraph(
            popBackStack = navigator.navController::popBackStack
        )

        recipeGraph(
            navigateToHome = navigator::navigateToHome,
            navigateToAddRecipe = navigator::navigateToAddRecipe,
            navigateToRecipeInfo = navigator::navigateToRecipeInfo,
        )

        addRecipeGraph(
            popBackStack = navigator.navController::popBackStack,
            launchMediaImagePermission = ingredientFarmingPermission::launchMediaImagesPermission,
        )

        recipeInfoGraph(
            popBackStack = navigator.navController::popBackStack
        )
    }
}
