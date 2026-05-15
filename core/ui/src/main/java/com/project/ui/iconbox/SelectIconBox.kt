package com.project.ui.iconbox

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.designsystem.theme.Green
import com.project.designsystem.theme.MoreLightGreen
import com.project.model.ingredient.IngredientCategory
import com.project.ui.IngredientFarmingIcon
import com.project.ui.R
import com.project.ui.util.iconResource

@Composable
internal fun SelectIconBox(
    modifier: Modifier = Modifier,
    title: String,
    selected: Boolean,
    iconBoxSize: IconBoxSize,
    icon: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .size(iconBoxSize.boxSize.dp)
            .border(
                width = 1.dp,
                color = if (selected) Green else Color.LightGray,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .background(color = if (selected) MoreLightGreen else Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        icon()

        Text(
            text = title,
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Preview
@Composable
private fun SelectedIconBoxPreview() {
    val category = IngredientCategory.FRUIT
    SelectIconBox(
        title = category.title,
        selected = true,
        iconBoxSize = IconBoxSize.MEDIUM

    ) {
        IngredientFarmingIcon(
            modifier = Modifier.size(IconBoxSize.SMALL.iconSize.dp),
            iconResource = category.iconResource,
            contentDescription = stringResource(R.string.icon_description, category.title),
            tint = Color(category.color),
        )
    }
}

@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true
)
@Composable
private fun UnSelectedIconBoxPreview() {
    val category = IngredientCategory.FRUIT
    SelectIconBox(
        title = category.title,
        selected = false,
        iconBoxSize = IconBoxSize.MEDIUM

    ) {
        IngredientFarmingIcon(
            modifier = Modifier.size(IconBoxSize.SMALL.iconSize.dp),
            iconResource = category.iconResource,
            contentDescription = stringResource(R.string.icon_description, category.title),
            tint = Color(category.color),
        )
    }
}
