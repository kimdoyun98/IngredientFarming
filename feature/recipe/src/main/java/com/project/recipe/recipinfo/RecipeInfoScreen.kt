package com.project.recipe.recipinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.designsystem.compose.IngredientFarmingWideButton
import com.project.designsystem.theme.Green
import com.project.designsystem.theme.MoreLightGreen
import com.project.model.recipe.IngredientUnit
import com.project.model.recipe.RecipeCategory
import com.project.model.recipe.RecipeStep
import com.project.recipe.R
import com.project.recipe.component.RecipeInfoContent
import com.project.recipe.recipinfo.component.ImageBox
import com.project.recipe.recipinfo.contract.RecipeInfoIntent
import com.project.recipe.recipinfo.contract.RecipeInfoState
import com.project.recipe.recipinfo.model.RecipeIngredientUiModel
import com.project.ui.AppBarType
import com.project.ui.IngredientFarmingTopAppBar
import com.project.ui.modifier.shadowLayout
import com.project.ui.util.format
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun RecipeInfoScreen(
    state: RecipeInfoState,
    onIntent: (RecipeInfoIntent) -> Unit,
) {
    RecipeInfoScreen(
        name = state.name,
        imagePath = state.imagePath,
        category = state.category,
        minute = state.minute,
        people = state.people,
        ingredients = state.ingredients,
        recipeSteps = state.recipeSteps,
        onClickNavigation = { onIntent(RecipeInfoIntent.OnTopAppBarNavigationClick) },
        onClickAddRequireIngredientButton = { onIntent(RecipeInfoIntent.OnAddRequireIngredientButtonClick) },
    )
}

@Composable
internal fun RecipeInfoScreen(
    modifier: Modifier = Modifier,
    name: String,
    imagePath: String?,
    category: RecipeCategory,
    minute: Int,
    people: Int,
    ingredients: ImmutableList<RecipeIngredientUiModel>,
    recipeSteps: ImmutableList<RecipeStep>,
    onClickNavigation: () -> Unit,
    onClickAddRequireIngredientButton: () -> Unit,
) {
    IngredientFarmingTopAppBar(
        type = AppBarType.Navigation,
        onClickNavigation = onClickNavigation,
        title = stringResource(R.string.recipe_info_top_app_bar_title)
    ) { innerPadding ->
        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            ImageBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                imagePath = imagePath,
            )

            RecipeTitleContent(name = name, category = category)

            Spacer(modifier = Modifier.height(8.dp))

            RecipeInfoContent(
                modifier = Modifier,
                time = minute,
                people = people
            )

            Spacer(modifier = Modifier.height(24.dp))

            RecipeIngredientsContent(
                ingredients = ingredients,
                onClickAddRequireIngredientButton = onClickAddRequireIngredientButton
            )

            Spacer(modifier = Modifier.height(16.dp))

            RecipeStepsContent(recipeSteps = recipeSteps)
        }
    }
}

@Composable
private fun RecipeTitleContent(
    name: String, category:
    RecipeCategory
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "(${category.title})",
            style = MaterialTheme.typography.bodyMedium,
            color = Gray
        )
    }
}

@Composable
private fun RecipeIngredientsContent(
    ingredients: ImmutableList<RecipeIngredientUiModel>,
    onClickAddRequireIngredientButton: () -> Unit,
) {
    Text(
        text = stringResource(R.string.recipe_info_require_ingredient),
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(8.dp))

    Column(
        modifier = Modifier
            .shadowLayout()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        ingredients.forEach { ingredient ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (ingredient.isAvailable) {
                    Icon(
                        imageVector = Icons.Outlined.CheckCircle,
                        contentDescription = stringResource(R.string.recipe_info_check_circle_icon_description),
                        tint = Green
                    )
                } else {
                    Icon(
                        imageVector = Icons.Outlined.Cancel,
                        contentDescription = stringResource(R.string.recipe_info_cancel_circle_icon_description),
                        tint = Red
                    )
                }

                Text(
                    modifier = Modifier.weight(1f),
                    text = ingredient.name,
                    color = DarkGray
                )

                Text(
                    text = ingredient.count.format(ingredient.unit),
                    color = DarkGray
                )
            }
        }
    }

    if(ingredients.any { !it.isAvailable }){
        IngredientFarmingWideButton(
            onClick = onClickAddRequireIngredientButton,
            background = Red
        ) { Text("부족한 재료 장보기 목록에 추가") }
    }
}

@Composable
private fun RecipeStepsContent(
    recipeSteps: ImmutableList<RecipeStep>
) {
    Text(
        text = stringResource(R.string.recipe_info_ingredients_cooking_step),
        style = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(8.dp))

    recipeSteps.forEach { step ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .shadowLayout()
                .padding(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(MoreLightGreen),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${step.number}",
                    color = Green
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = step.description,
                color = DarkGray
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Preview
@Composable
private fun RecipeInfoScreenPreview() {
    RecipeInfoScreen(
        state = RecipeInfoState(
            name = "비빔밥",
            imagePath = null,
            category = RecipeCategory.KOREAN_FOOD,
            minute = 30,
            people = 2,
            ingredients = persistentListOf(
                RecipeIngredientUiModel(
                    id = 0,
                    ingredientId = 0,
                    name = "김치",
                    count = 1.0,
                    unit = IngredientUnit.COUNT,
                    isAvailable = true
                ),
                RecipeIngredientUiModel(
                    id = 1,
                    ingredientId = 1,
                    name = "다진마늘",
                    count = 1.0,
                    unit = IngredientUnit.TABLESPOON,
                    isAvailable = false
                )
            ),
            recipeSteps = persistentListOf(
                RecipeStep(
                    id = 0,
                    number = 1,
                    description = "소고기는 얇게 채 썰어 간장, 설탕, 다진 마늘로 밑간합니다."
                ),
                RecipeStep(
                    id = 1,
                    number = 2,
                    description = "당근, 시금치, 콩나물, 표고버섯을 각각 데치거나 볶아 간을 합니다."
                )
            )
        ),
        onIntent = {}
    )
}
