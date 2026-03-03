package com.project.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
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

enum class IconBoxSize(val boxSize: Int, val iconSize: Int) {
    SMALL(40, 24),
    MEDIUM(60, 36),
    LARGE(80, 48),
}

@Composable
fun SmallIconBox(
    modifier: Modifier = Modifier,
    iconBackgroundColor: Color,
    icon: @Composable () -> Unit
) {
    IconBox(
        modifier = modifier,
        iconBackgroundColor = iconBackgroundColor,
        iconBoxSize = IconBoxSize.SMALL,
        icon = icon
    )
}

@Composable
fun MediumIconBox(
    modifier: Modifier = Modifier,
    iconBackgroundColor: Color,
    icon: @Composable () -> Unit
) {
    IconBox(
        modifier = modifier,
        iconBackgroundColor = iconBackgroundColor,
        iconBoxSize = IconBoxSize.MEDIUM,
        icon = icon
    )
}


@Composable
internal fun IconBox(
    modifier: Modifier = Modifier,
    iconBackgroundColor: Color,
    iconBoxSize: IconBoxSize,
    icon: @Composable () -> Unit,
) {
    Box(
        modifier = modifier
            .size(iconBoxSize.boxSize.dp)
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

@Preview
@Composable
private fun SmallIconBoxPreview() {
    val category = IngredientCategory.FRUIT
    SmallIconBox(
        iconBackgroundColor = Color(category.background),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_apple),
            contentDescription = stringResource(R.string.fruit_icon_description),
            tint = Color(category.color)
        )
    }
}

@Preview
@Composable
private fun MediumIconBoxPreview() {
    val category = IngredientCategory.FRUIT
    IconBox(
        iconBackgroundColor = Color(category.background),
        iconBoxSize = IconBoxSize.MEDIUM
    ) {
        Icon(
            modifier = Modifier.size(48.dp),
            painter = painterResource(id = R.drawable.ic_apple),
            contentDescription = stringResource(R.string.fruit_icon_description),
            tint = Color(category.color)
        )
    }
}

@Preview
@Composable
private fun LargeIconBoxPreview() {
    val category = IngredientCategory.FRUIT
    IconBox(
        iconBackgroundColor = Color(category.background),
        iconBoxSize = IconBoxSize.LARGE
    ) {
        Icon(
            modifier = Modifier.size(72.dp),
            painter = painterResource(id = R.drawable.ic_apple),
            contentDescription = stringResource(R.string.fruit_icon_description),
            tint = Color(category.color)
        )
    }
}
