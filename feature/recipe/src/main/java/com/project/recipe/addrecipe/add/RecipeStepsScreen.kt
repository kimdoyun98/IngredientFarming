package com.project.recipe.addrecipe.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.designsystem.compose.IngredientFarmingWideButton
import com.project.designsystem.compose.LocarmTextField
import com.project.designsystem.theme.Green
import com.project.designsystem.theme.LightGreen
import com.project.recipe.R
import com.project.recipe.addrecipe.component.ContentTitle
import com.project.recipe.addrecipe.component.MainTitleContent
import com.project.recipe.addrecipe.contract.AddRecipeIntent
import com.project.recipe.addrecipe.contract.AddRecipeState
import com.project.recipe.addrecipe.model.RecipeStepUiModel
import com.project.recipe.addrecipe.util.copyToInternalStorage
import com.project.ui.SmallIconBox
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun RecipeStepsScreen(
    modifier: Modifier = Modifier,
    state: AddRecipeState,
    onIntent: (AddRecipeIntent) -> Unit,
) {
    val context = LocalContext.current
    RecipeStepsScreen(
        modifier = modifier,
        steps = state.recipeSteps,
        saveButtonEnable = state.isEnableSaveButton,
        onRecipeStepChange = { step, value ->
            onIntent(
                AddRecipeIntent.RecipeStep.RecipeStepChange(
                    step,
                    value
                )
            )
        },
        onAddStepButtonClick = { onIntent(AddRecipeIntent.RecipeStep.StepAddButtonClick) },
        onRecipeDeleteButtonClick = { step ->
            onIntent(
                AddRecipeIntent.RecipeStep.StepDeleteButtonClick(
                    step
                )
            )
        },
        onSaveButtonClick = {
            onIntent(
                AddRecipeIntent.RecipeStep.RecipeSaveButtonClick(
                    filePath = state.photo?.copyToInternalStorage(
                        context
                    )
                )
            )
        }
    )
}

@Composable
internal fun RecipeStepsScreen(
    modifier: Modifier = Modifier,
    steps: ImmutableList<RecipeStepUiModel>,
    saveButtonEnable: Boolean,
    onRecipeStepChange: (RecipeStepUiModel, String) -> Unit,
    onAddStepButtonClick: () -> Unit,
    onRecipeDeleteButtonClick: (RecipeStepUiModel) -> Unit,
    onSaveButtonClick: () -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            item {
                MainTitleContent(
                    title = stringResource(R.string.add_recipe_ingredients_step_main_title)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ContentTitle(stringResource(R.string.add_recipe_ingredients_cooking_step))
                    TextButton(
                        onClick = onAddStepButtonClick
                    ) {
                        Text(
                            text = stringResource(R.string.add_recipe_add_text_button_title),
                            color = Green
                        )
                    }
                }
            }

            itemsIndexed(
                items = steps,
                key = { _, step -> step.id }
            ) { idx, step ->
                RecipeStepItem(
                    number = idx,
                    descriptions = step.description,
                    onRecipeStepChange = { value -> onRecipeStepChange(step, value) },
                    onRecipeDeleteButtonClick = { onRecipeDeleteButtonClick(step) }
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        IngredientFarmingWideButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = onSaveButtonClick,
            background = Green,
            enabled = saveButtonEnable
        ) {
            Text(stringResource(R.string.add_recipe_save_button_title))
        }
    }
}


@Composable
private fun RecipeStepItem(
    number: Int,
    descriptions: String,
    onRecipeStepChange: (String) -> Unit,
    onRecipeDeleteButtonClick: () -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        SmallIconBox(
            iconBackgroundColor = LightGreen,
        ) {
            Text("${number + 1}")
        }

        Spacer(modifier = Modifier.width(8.dp))

        LocarmTextField(
            modifier = Modifier.weight(1f),
            value = descriptions,
            placeholder = stringResource(R.string.add_recipe_ingredients_cooking_step_placeholder),
            onValueChange = { onRecipeStepChange(it) }
        )

        Spacer(modifier = Modifier.width(4.dp))

        IconButton(
            onClick = onRecipeDeleteButtonClick
        ) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = ""
            )
        }
    }
}

@Preview
@Composable
private fun RecipeStepsScreenPreview() {
    RecipeStepsScreen(
        state = AddRecipeState(),
        onIntent = {}
    )
}
