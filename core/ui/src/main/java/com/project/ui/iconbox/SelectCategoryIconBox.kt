package com.project.ui.iconbox

import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.model.ingredient.IngredientCategory
import com.project.ui.LocarmIcon
import com.project.ui.R
import com.project.ui.util.iconResource

@Composable
fun SelectCategoryIconBox(
    modifier: Modifier = Modifier,
    category: IngredientCategory,
    selected: Boolean
) {
    SelectIconBox(
        modifier = modifier,
        title = category.title,
        selected = selected,
        iconBoxSize = IconBoxSize.MEDIUM
    ) {
        LocarmIcon(
            modifier = Modifier.size(IconBoxSize.SMALL.iconSize.dp),
            iconResource = category.iconResource,
            contentDescription = stringResource(R.string.icon_description, category.title),
            tint = Color(category.color),
        )
    }
}

@Preview
@Composable
private fun SelectCategoryIconBoxPreview() {
    SelectCategoryIconBox(
        category = IngredientCategory.VEGETABLE,
        selected = true
    )
}

@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true
)
@Composable
private fun UnSelectCategoryIconBoxPreview() {
    SelectCategoryIconBox(
        category = IngredientCategory.VEGETABLE,
        selected = false
    )
}
