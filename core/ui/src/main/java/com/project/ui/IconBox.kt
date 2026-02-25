package com.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.Cookie
import androidx.compose.material.icons.outlined.EggAlt
import androidx.compose.material.icons.outlined.LocalCafe
import androidx.compose.material.icons.outlined.PropaneTank
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.model.ingredient.IngredientCategory

@Composable
fun SmallIconBox(
    modifier: Modifier = Modifier,
    iconBackgroundColor: Color,
    icon: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .size(40.dp)
            .border(
                width = 1.dp,
                color = iconBackgroundColor,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .background(color = iconBackgroundColor),
        contentAlignment = Alignment.Center
    ) {
        icon()
    }
}

@Composable
fun CategorySmallIconBox(
    modifier: Modifier,
    category: IngredientCategory
) {
    SmallIconBox(
        modifier = modifier,
        iconBackgroundColor = Color(category.background),
    ) {
        when (category) {
            IngredientCategory.CONDIMENT -> {
                Icon(
                    imageVector = Icons.Outlined.PropaneTank,
                    contentDescription = stringResource(R.string.condiment_icon_description),
                    tint = Color(category.color)
                )
            }

            IngredientCategory.VEGETABLE -> {
                Icon(
                    painter = painterResource(id = R.drawable.ic_vegetable),
                    contentDescription = stringResource(R.string.vegetable_icon_description),
                    tint = Color(category.color)
                )
            }

            IngredientCategory.FRUIT -> {
                Icon(
                    painter = painterResource(id = R.drawable.ic_apple),
                    contentDescription = stringResource(R.string.fruit_icon_description),
                    tint = Color(category.color)
                )
            }

            IngredientCategory.MEAT -> {
                Icon(
                    imageVector = Icons.Outlined.EggAlt,
                    contentDescription = stringResource(R.string.meat_icon_description),
                    tint = Color(category.color)
                )
            }

            IngredientCategory.DAIRY -> {
                Icon(
                    painter = painterResource(id = R.drawable.ic_milk),
                    contentDescription = stringResource(R.string.dairy_icon_description),
                    tint = Color(category.color)
                )
            }

            IngredientCategory.GRAIN -> {
                Icon(
                    imageVector = Icons.Outlined.Spa,
                    contentDescription = stringResource(R.string.grain_icon_description),
                    tint = Color(category.color)
                )
            }

            IngredientCategory.BEVERAGE -> {
                Icon(
                    imageVector = Icons.Outlined.LocalCafe,
                    contentDescription = stringResource(R.string.beverage_icon_description),
                    tint = Color(category.color)
                )
            }

            IngredientCategory.SNACK -> {
                Icon(
                    imageVector = Icons.Outlined.Cookie,
                    contentDescription = stringResource(R.string.snack_icon_description),
                    tint = Color(category.color)
                )
            }

            else -> {
                Icon(
                    imageVector = Icons.Default.MoreHoriz,
                    contentDescription = stringResource(R.string.other_icon_description),
                    tint = Color(category.color)
                )
            }
        }
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
