package com.project.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.Cookie
import androidx.compose.material.icons.outlined.EggAlt
import androidx.compose.material.icons.outlined.LocalCafe
import androidx.compose.material.icons.outlined.PropaneTank
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.model.ingredient.IngredientCategory

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
            iconResource = when (category) {
                IngredientCategory.CONDIMENT -> {
                    IconResource.IsImageVector(Icons.Outlined.PropaneTank)
                }

                IngredientCategory.VEGETABLE -> {
                    IconResource.IsPainter(id = R.drawable.ic_vegetable)
                }

                IngredientCategory.FRUIT -> {
                    IconResource.IsPainter(id = R.drawable.ic_apple)
                }

                IngredientCategory.MEAT -> {
                    IconResource.IsImageVector(Icons.Outlined.EggAlt)
                }

                IngredientCategory.DAIRY -> {
                    IconResource.IsPainter(id = R.drawable.ic_milk)
                }

                IngredientCategory.GRAIN -> {
                    IconResource.IsImageVector(Icons.Outlined.Spa)
                }

                IngredientCategory.BEVERAGE -> {
                    IconResource.IsImageVector(Icons.Outlined.LocalCafe)
                }

                IngredientCategory.SNACK -> {
                    IconResource.IsImageVector(Icons.Outlined.Cookie)
                }

                IngredientCategory.OTHER -> {
                    IconResource.IsImageVector(Icons.Default.MoreHoriz)
                }
            },
            contentDescription = category.title,
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
