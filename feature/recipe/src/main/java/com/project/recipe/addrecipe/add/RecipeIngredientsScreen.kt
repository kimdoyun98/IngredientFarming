package com.project.recipe.addrecipe.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.designsystem.compose.IngredientFarmingWideButton
import com.project.designsystem.compose.LocarmNumberTextField
import com.project.designsystem.compose.LocarmTextField
import com.project.designsystem.theme.Green
import com.project.model.recipe.IngredientUnit
import com.project.recipe.R
import com.project.recipe.addrecipe.component.ContentTitle
import com.project.recipe.addrecipe.component.MainTitleContent
import com.project.recipe.addrecipe.contract.AddRecipeIntent
import com.project.recipe.addrecipe.contract.AddRecipeState
import com.project.recipe.addrecipe.model.IngredientUiModel
import com.project.ui.modifier.shadowLayout
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun RecipeIngredientsScreen(
    modifier: Modifier = Modifier,
    state: AddRecipeState,
    onIntent: (AddRecipeIntent) -> Unit,
) {
    RecipeIngredientsScreen(
        modifier = modifier,
        ingredients = state.ingredients,
        nextButtonEnable = state.isEnableIngredientsNextButton,
        onAddIngredientButtonClick = { onIntent(AddRecipeIntent.Ingredients.IngredientAddButtonClick) },
        onNextButtonClick = { onIntent(AddRecipeIntent.Ingredients.RecipeIngredientsNextButtonClick) },
        onIngredientNameChange = { ingredient, name ->
            onIntent(
                AddRecipeIntent.Ingredients.IngredientNameChange(
                    ingredient,
                    name
                )
            )
        },
        onIngredientAmountChange = { ingredient, amount ->
            onIntent(
                AddRecipeIntent.Ingredients.IngredientAmountChange(
                    ingredient,
                    amount
                )
            )
        },
        onIngredientUnitChange = { ingredient, unit ->
            onIntent(
                AddRecipeIntent.Ingredients.IngredientUnitChange(
                    ingredient,
                    unit
                )
            )
        },
        onDeleteItem = { onIntent(AddRecipeIntent.Ingredients.IngredientDeleteButtonClick(it)) }
    )
}

@Composable
internal fun RecipeIngredientsScreen(
    modifier: Modifier = Modifier,
    ingredients: ImmutableList<IngredientUiModel>,
    nextButtonEnable: Boolean,
    onAddIngredientButtonClick: () -> Unit,
    onNextButtonClick: () -> Unit,
    onIngredientNameChange: (IngredientUiModel, String) -> Unit,
    onIngredientAmountChange: (IngredientUiModel, String) -> Unit,
    onIngredientUnitChange: (IngredientUiModel, IngredientUnit) -> Unit,
    onDeleteItem: (IngredientUiModel) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            item {
                MainTitleContent(
                    title = stringResource(R.string.add_recipe_ingredients_main_title)
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    ContentTitle(stringResource(R.string.add_recipe_ingredients_ingredient))
                    TextButton(
                        onClick = onAddIngredientButtonClick
                    ) {
                        Text(
                            text = stringResource(R.string.add_recipe_add_text_button_title),
                            color = Green
                        )
                    }
                }
            }

            items(
                items = ingredients,
                key = { it.id }
            ) {
                IngredientItem(
                    ingredientName = it.name,
                    ingredientAmount = it.amount,
                    unitSelected = it.unit,
                    onIngredientNameChange = { name -> onIngredientNameChange(it, name) },
                    onIngredientAmountChange = { amount -> onIngredientAmountChange(it, amount) },
                    onIngredientUnitChange = { unit -> onIngredientUnitChange(it, unit) },
                    onDeleteItem = { onDeleteItem(it) }
                )

                Spacer(modifier = Modifier.height(8.dp))
            }
        }

        IngredientFarmingWideButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = onNextButtonClick,
            background = Green,
            enabled = nextButtonEnable
        ) {
            Text(stringResource(R.string.add_recipe_next_button_title))
        }
    }
}

@Composable
private fun IngredientItem(
    ingredientName: String,
    ingredientAmount: String,
    unitSelected: IngredientUnit,
    onIngredientNameChange: (String) -> Unit,
    onIngredientAmountChange: (String) -> Unit,
    onIngredientUnitChange: (IngredientUnit) -> Unit,
    onDeleteItem: () -> Unit,
) {
    Column(
        modifier = Modifier
            .shadowLayout()
            .padding(8.dp)
    ) {
        LocarmTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = ingredientName,
            placeholder = stringResource(R.string.add_recipe_ingredients_ingredient_name_placeholder),
            onValueChange = onIngredientNameChange,
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f)
            )
        )

        Spacer(modifier = Modifier.height(4.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            LocarmNumberTextField(
                modifier = Modifier.weight(2f),
                value = ingredientAmount,
                placeholder = stringResource(R.string.add_recipe_ingredients_ingredient_amount_placeholder),
                onValueChange = onIngredientAmountChange,
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                    unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f)
                )
            )

            Spacer(modifier = Modifier.width(4.dp))

            UnitDropdown(
                modifier = Modifier.weight(1f),
                unitSelected
            ) { onIngredientUnitChange(it) }

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(
                onClick = { onDeleteItem() }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = ""
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun UnitDropdown(
    modifier: Modifier = Modifier,
    selected: IngredientUnit,
    onSelected: (IngredientUnit) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable),
            value = selected.label,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.3f)
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            IngredientUnit.entries.forEach { unit ->
                DropdownMenuItem(
                    text = { Text(unit.label) },
                    onClick = {
                        onSelected(unit)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview
@Composable
private fun RecipeIngredientsScreenPreview() {
    RecipeIngredientsScreen(
        state = AddRecipeState(),
        onIntent = {}
    )
}
