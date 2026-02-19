package com.project.designsystem.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun IngredientFarmingWideButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        onClick = { onClick() },
        enabled = enabled,
        content = { content() }
    )
}

@Composable
fun IngredientFarmingRoundedIconButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color? = null,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    FilledIconButton(
        modifier = modifier
            .padding(4.dp)
            .size(60.dp),
        shape = RoundedCornerShape(12.dp),
        colors = if (backgroundColor == null) {
            IconButtonDefaults.filledIconButtonColors()
        } else {
            IconButtonDefaults.iconButtonColors(
                containerColor = backgroundColor
            )
        },
        onClick = { onClick() },
        content = { content() }
    )
}

@Preview
@Composable
private fun IngredientFarmingWideButtonPreview() {
    IngredientFarmingWideButton(
        onClick = {}
    ) {
        Text("다음")
    }
}

@Preview
@Composable
private fun IngredientFarmingIconButtonPreview() {
    IngredientFarmingRoundedIconButton(
        onClick = {}
    ) {
        Icon(
            imageVector = Icons.Outlined.ShoppingCart,
            contentDescription = null,
            tint = Color.Magenta
        )
    }
}
