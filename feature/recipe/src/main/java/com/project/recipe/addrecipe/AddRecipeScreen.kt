package com.project.recipe.addrecipe

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.model.permission.PermissionState
import com.project.recipe.addrecipe.add.RecipeBasicInfoScreen
import com.project.recipe.addrecipe.add.RecipeIngredientsScreen
import com.project.recipe.addrecipe.add.RecipePhotoScreen
import com.project.recipe.addrecipe.add.RecipeStepsScreen
import com.project.recipe.addrecipe.contract.AddRecipeIntent
import com.project.recipe.addrecipe.contract.AddRecipeState
import com.project.recipe.addrecipe.util.AddRecipeBackStack
import com.project.recipe.addrecipe.util.RecipeSaveState
import com.project.ui.AppBarType
import com.project.ui.IngredientFarmingCenterLoading
import com.project.ui.IngredientFarmingTopAppBar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

@Composable
internal fun AddRecipeScreen(
    modifier: Modifier = Modifier,
    state: AddRecipeState,
    snackbarHostState: SnackbarHostState,
    mediaImagePermissionState: Flow<PermissionState>,
    onIntent: (AddRecipeIntent) -> Unit,
    launchMediaImagePermission: () -> Unit,
) {
    val title = remember(state.currentBackstack) {
        when (val backstack = state.currentBackstack) {
            is AddRecipeBackStack.RecipePhotoScreen -> backstack.title
            is AddRecipeBackStack.RecipeBasicInfoScreen -> backstack.title
            is AddRecipeBackStack.RecipeIngredientsScreen -> backstack.title
            is AddRecipeBackStack.RecipeStepsScreen -> backstack.title
        }
    }

    IngredientFarmingTopAppBar(
        type = AppBarType.Navigation,
        title = stringResource(title),
        onClickNavigation = { onIntent(AddRecipeIntent.Back) },
        snackBarHostState = snackbarHostState,
    ) { innerPadding ->
        Box(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when (state.currentBackstack) {
                is AddRecipeBackStack.RecipePhotoScreen -> {
                    RecipePhotoScreen(
                        modifier = Modifier
                            .fillMaxSize(),
                        state = state,
                        mediaImagePermissionState = mediaImagePermissionState,
                        onIntent = onIntent,
                        launchMediaImagePermission = launchMediaImagePermission,
                    )
                }

                is AddRecipeBackStack.RecipeBasicInfoScreen -> {
                    RecipeBasicInfoScreen(
                        modifier = Modifier
                            .fillMaxSize(),
                        state = state,
                        onIntent = onIntent
                    )
                }

                is AddRecipeBackStack.RecipeIngredientsScreen -> {
                    RecipeIngredientsScreen(
                        modifier = Modifier
                            .fillMaxSize(),
                        state = state,
                        onIntent = onIntent
                    )
                }

                is AddRecipeBackStack.RecipeStepsScreen -> {
                    RecipeStepsScreen(
                        modifier = Modifier
                            .fillMaxSize(),
                        state = state,
                        onIntent = onIntent
                    )
                }
            }

            if (state.recipeSaveState is RecipeSaveState.Loading) {
                IngredientFarmingCenterLoading(
                    modifier = Modifier
                        .align(Alignment.Center)
                )
            }
        }
    }

    BackHandler {
        onIntent(AddRecipeIntent.Back)
    }
}

@Preview
@Composable
private fun AddRecipeScreenPreview() {
    AddRecipeScreen(
        state = AddRecipeState(
            currentBackstack = AddRecipeBackStack.RecipePhotoScreen()
        ),
        mediaImagePermissionState = MutableSharedFlow(),
        onIntent = {},
        launchMediaImagePermission = {},
        snackbarHostState = remember { SnackbarHostState() }
    )
}
