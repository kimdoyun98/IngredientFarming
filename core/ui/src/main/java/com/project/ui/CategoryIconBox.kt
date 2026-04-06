package com.project.ui

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.model.ingredient.IngredientCategory
import com.project.ui.util.iconResource

@Composable
fun CategorySmallIconBox(
    modifier: Modifier = Modifier,
    category: IngredientCategory
) {
    CategoryIconBox(
        modifier = modifier,
        category = category,
        iconBoxSize = IconBoxSize.SMALL
    )
}

@Composable
fun CategoryMediumIconBox(
    modifier: Modifier = Modifier,
    category: IngredientCategory
) {
    CategoryIconBox(
        modifier = modifier,
        category = category,
        iconBoxSize = IconBoxSize.MEDIUM
    )
}

@Composable
fun CategoryLargeIconBox(
    modifier: Modifier = Modifier,
    category: IngredientCategory
) {
    CategoryIconBox(
        modifier = modifier,
        category = category,
        iconBoxSize = IconBoxSize.LARGE
    )
}

@Composable
internal fun CategoryIconBox(
    modifier: Modifier = Modifier,
    category: IngredientCategory,
    iconBoxSize: IconBoxSize,
) {
    IconBox(
        modifier = modifier,
        iconBackgroundColor = Color(category.background),
        iconBoxSize = iconBoxSize,
    ) {
        LocarmIcon(
            modifier = Modifier.size(iconBoxSize.iconSize.dp),
            iconResource = category.iconResource,
            contentDescription = stringResource(R.string.icon_description, category.title),
            tint = Color(category.color),
        )
    }
}

@Preview(name = "양념")
@Composable
private fun CondimentCategorySmallIconBoxPreview() {
    CategorySmallIconBox(
        modifier = Modifier,
        category = IngredientCategory.CONDIMENT
    )
}

@Preview(name = "채소")
@Composable
private fun VegetableCategorySmallIconBoxPreview() {
    CategorySmallIconBox(
        modifier = Modifier,
        category = IngredientCategory.VEGETABLE
    )
}

@Preview(name = "과일")
@Composable
private fun FruitCategorySmallIconBoxPreview() {
    CategorySmallIconBox(
        modifier = Modifier,
        category = IngredientCategory.FRUIT
    )
}

@Preview(name = "육류")
@Composable
private fun MeatCategorySmallIconBoxPreview() {
    CategorySmallIconBox(
        modifier = Modifier,
        category = IngredientCategory.MEAT
    )
}

@Preview(name = "유제품")
@Composable
private fun DairyCategorySmallIconBoxPreview() {
    CategorySmallIconBox(
        modifier = Modifier,
        category = IngredientCategory.DAIRY
    )
}

@Preview(name = "곡물")
@Composable
private fun GrainCategorySmallIconBoxPreview() {
    CategorySmallIconBox(
        modifier = Modifier,
        category = IngredientCategory.GRAIN
    )
}

@Preview(name = "음료")
@Composable
private fun BeverageCategorySmallIconBoxPreview() {
    CategorySmallIconBox(
        modifier = Modifier,
        category = IngredientCategory.BEVERAGE
    )
}

@Preview(name = "간식")
@Composable
private fun SnackCategorySmallIconBoxPreview() {
    CategorySmallIconBox(
        modifier = Modifier,
        category = IngredientCategory.SNACK
    )
}

@Preview(name = "기타")
@Composable
private fun OtherCategorySmallIconBoxPreview() {
    CategorySmallIconBox(
        modifier = Modifier,
        category = IngredientCategory.OTHER
    )
}
