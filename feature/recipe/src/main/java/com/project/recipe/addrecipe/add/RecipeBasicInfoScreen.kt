package com.project.recipe.addrecipe.add

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.outlined.PeopleOutline
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.designsystem.compose.IngredientFarmingWideButton
import com.project.designsystem.compose.LocarmNumberTextField
import com.project.designsystem.compose.LocarmTextField
import com.project.designsystem.theme.Green
import com.project.model.recipe.RecipeCategory
import com.project.recipe.R
import com.project.recipe.addrecipe.component.ContentTitle
import com.project.recipe.addrecipe.component.IconContentTitle
import com.project.recipe.addrecipe.component.MainTitleContent
import com.project.recipe.addrecipe.contract.AddRecipeIntent
import com.project.recipe.addrecipe.contract.AddRecipeState

@Composable
internal fun RecipeBasicInfoScreen(
    modifier: Modifier = Modifier,
    state: AddRecipeState,
    onIntent: (AddRecipeIntent) -> Unit,
) {
    RecipeBasicInfoScreen(
        modifier = modifier,
        name = state.name,
        cookTime = state.time,
        people = state.people,
        selectedCategory = state.selectedCategory,
        onNameValueChange = { onIntent(AddRecipeIntent.BasicInfo.RecipeNameChange(it)) },
        onSelectCategoryChip = { onIntent(AddRecipeIntent.BasicInfo.SelectCategoryChip(it)) },
        onCookTimeValueChange = { onIntent(AddRecipeIntent.BasicInfo.RecipeCookTimeChange(it)) },
        onPeopleValueChange = { onIntent(AddRecipeIntent.BasicInfo.RecipePeopleChange(it)) },
        onNextButtonClick = { onIntent(AddRecipeIntent.BasicInfo.RecipeBasicInfoNextButtonClick) }
    )
}

@Composable
internal fun RecipeBasicInfoScreen(
    modifier: Modifier = Modifier,
    name: String,
    cookTime: String,
    people: String,
    selectedCategory: RecipeCategory?,
    onNameValueChange: (String) -> Unit,
    onSelectCategoryChip: (RecipeCategory) -> Unit,
    onCookTimeValueChange: (String) -> Unit,
    onPeopleValueChange: (String) -> Unit,
    onNextButtonClick: () -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            MainTitleContent(
                title = stringResource(R.string.add_recipe_basic_info_main_title)
            )

            LocarmTextField(
                modifier = Modifier
                    .fillMaxWidth(),
                label = stringResource(R.string.add_recipe_basic_info_recipe_name_label),
                placeholder = stringResource(R.string.add_recipe_basic_info_recipe_name_placeholder),
                value = name,
                onValueChange = onNameValueChange
            )

            Spacer(modifier = Modifier.height(16.dp))

            ContentTitle(stringResource(R.string.add_recipe_basic_info_cook_category))
            RecipeCategoryFilterChipGroup(
                selectedCategory = selectedCategory,
                onSelectCategoryChip = { category -> onSelectCategoryChip(category) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            TimeAndPeopleContent(
                cookTime = cookTime,
                people = people,
                onCookTimeValueChange = onCookTimeValueChange,
                onPeopleValueChange = onPeopleValueChange
            )
        }

        IngredientFarmingWideButton(
            modifier = Modifier.align(Alignment.BottomCenter),
            onClick = onNextButtonClick,
            background = Green
        ) {
            Text(stringResource(R.string.add_recipe_next_button_title))
        }
    }
}

@Composable
private fun RecipeCategoryFilterChipGroup(
    selectedCategory: RecipeCategory?,
    onSelectCategoryChip: (RecipeCategory) -> Unit,
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(RecipeCategory.entries) { category ->
            FilterChip(
                label = { Text(category.title) },
                onClick = { onSelectCategoryChip(category) },
                selected = selectedCategory == category
            )
        }
    }
}

@Composable
private fun TimeAndPeopleContent(
    cookTime: String,
    people: String,
    onCookTimeValueChange: (String) -> Unit,
    onPeopleValueChange: (String) -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            IconContentTitle(stringResource(R.string.add_recipe_basic_info_cook_time)) {
                Icon(
                    imageVector = Icons.Default.AccessTime,
                    contentDescription = stringResource(R.string.add_recipe_basic_info_cook_time_icon_description)
                )
            }

            LocarmNumberTextField(
                modifier = Modifier,
                placeholder = stringResource(R.string.add_recipe_basic_info_cook_time_placeholder),
                value = cookTime,
                onValueChange = { onCookTimeValueChange(it) },
                trailingIcon = { Text(stringResource(R.string.add_recipe_basic_info_cook_time_trailing_icon)) }
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            IconContentTitle(stringResource(R.string.add_recipe_basic_info_people)) {
                Icon(
                    imageVector = Icons.Outlined.PeopleOutline,
                    contentDescription = stringResource(R.string.add_recipe_basic_info_people_icon_description)
                )
            }

            LocarmNumberTextField(
                modifier = Modifier,
                placeholder = stringResource(R.string.add_recipe_basic_info_people_placeholder),
                value = people,
                onValueChange = { onPeopleValueChange(it) },
                trailingIcon = { Text(stringResource(R.string.add_recipe_basic_info_people_trailing_icon)) }
            )
        }
    }
}

@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true
)
@Composable
private fun RecipeBasicInfoScreenPreview() {
    RecipeBasicInfoScreen(
        state = AddRecipeState(),
        onIntent = {}
    )
}
