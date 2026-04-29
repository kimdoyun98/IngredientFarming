package com.project.recipe.addrecipe.navigation

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.project.model.permission.PermissionState
import com.project.navigation.IngredientFarmingNavigator
import com.project.navigation.IngredientRoute
import com.project.recipe.R
import com.project.recipe.addrecipe.AddRecipeScreen
import com.project.recipe.addrecipe.AddRecipeViewModel
import com.project.recipe.addrecipe.contract.AddRecipeEffect
import com.project.recipe.addrecipe.contract.AddRecipeIntent
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

fun NavGraphBuilder.addRecipeGraph(
    navigator: IngredientFarmingNavigator,
    launchMediaImagePermission: ((PermissionState) -> Unit) -> Unit,
) {
    composable<IngredientRoute.AddRecipe> {
        val addRecipeViewModel: AddRecipeViewModel = hiltViewModel()
        val addRecipeState by addRecipeViewModel.collectAsState()
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        val context = LocalContext.current

        val uriFailMessage = stringResource(R.string.add_recipe_photo_fail_url_message)
        val mediaImagePermissionMessage =
            stringResource(R.string.add_recipe_photo_require_permission_message)
        val requestPermissionLabel = stringResource(R.string.add_recipe_photo_request_permission)
        val openAppSettingsLabel = stringResource(R.string.add_recipe_photo_open_app_setting)

        val pickMedia =
            rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                addRecipeViewModel.onIntent(AddRecipeIntent.Photo.RecipePhotoSelect(uri))
            }

        val permissionLaunch = {
            launchMediaImagePermission.invoke { state ->
                when (state) {
                    is PermissionState.Granted -> {
                        addRecipeViewModel.onIntent(AddRecipeIntent.Photo.PermissionGranted)
                    }

                    is PermissionState.Denied -> {
                        addRecipeViewModel.onIntent(AddRecipeIntent.Photo.PermissionDenied)
                    }

                    is PermissionState.PermanentlyDenied -> {
                        addRecipeViewModel.onIntent(
                            AddRecipeIntent.Photo.PermissionPermanentlyDenied(state.openAppSettings)
                        )
                    }
                }
            }
        }

        addRecipeViewModel.collectSideEffect { effect ->
            when (effect) {
                is AddRecipeEffect.NavigateToRecipeList -> {
                    navigator.navController.popBackStack()
                }

                is AddRecipeEffect.UriIsNull -> {
                    Toast.makeText(context, uriFailMessage, Toast.LENGTH_SHORT).show()
                }

                is AddRecipeEffect.SaveError -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }

                is AddRecipeEffect.PermissionGranted -> {
                    pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                }

                is AddRecipeEffect.PermissionDenied -> {
                    scope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = mediaImagePermissionMessage,
                            actionLabel = requestPermissionLabel,
                            duration = SnackbarDuration.Indefinite
                        )

                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                                permissionLaunch.invoke()
                            }

                            SnackbarResult.Dismissed -> Unit
                        }
                    }
                }

                is AddRecipeEffect.PermissionPermanentlyDenied -> {
                    scope.launch {
                        val result = snackbarHostState.showSnackbar(
                            message = mediaImagePermissionMessage,
                            actionLabel = openAppSettingsLabel,
                            duration = SnackbarDuration.Indefinite
                        )

                        when (result) {
                            SnackbarResult.ActionPerformed -> {
                                effect.openAppSettings()
                            }

                            SnackbarResult.Dismissed -> Unit
                        }
                    }
                }
            }
        }

        AddRecipeScreen(
            state = addRecipeState,
            onIntent = addRecipeViewModel::onIntent,
            snackbarHostState = snackbarHostState,
            launchMediaImagePermission = permissionLaunch
        )
    }
}
