package com.project.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.project.home.navigation.homeGraph
import com.project.ingredient.barcode.ui.barcode.navigation.barcodeScannerGraph
import com.project.ingredient.barcode.ui.directInput.navigation.directInputGraph
import com.project.ingredient.barcode.ui.save.navigation.saveIngredientGraph
import com.project.ingredient_manage.navigation.manageGraph
import com.project.ingredient_manage.navigation.updateHoldIngredientGraph
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
        homeGraph(navigator = navigator)

        barcodeScannerGraph(
            navigator = navigator,
            isGrantedCameraPermission = ingredientFarmingPermission::isGrantedCameraPermission,
            updateCameraPermissionState = { permissionState ->
                ingredientFarmingPermission.updateCameraPermissionState(permissionState)
            },
            cameraPermissionState = ingredientFarmingPermission.cameraPermissionState,
            requestCameraPermission = ingredientFarmingPermission::launchCameraPermission
        )

        saveIngredientGraph(navigator = navigator)

        directInputGraph(navigator = navigator)

        manageGraph(navigator = navigator)

        updateHoldIngredientGraph(navigator = navigator)

        shoppingCartGraph(navigator = navigator)

        recipeGraph(navigator = navigator)

        addRecipeGraph(
            navigator = navigator,
            launchMediaImagePermission = ingredientFarmingPermission::launchMediaImagesPermission,
        )

        recipeInfoGraph(navigator = navigator)
    }
}
