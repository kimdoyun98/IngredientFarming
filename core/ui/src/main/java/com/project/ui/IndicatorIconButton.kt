package com.project.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.designsystem.compose.IngredientFarmingRoundedIconButton
import com.project.ui.modifier.circleLayout

@Composable
fun IndicatorRoundedIconButton(
    modifier: Modifier = Modifier,
    indicatorValue: String? = null,
    backgroundColor: Color? = null,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
    ) {
        IngredientFarmingRoundedIconButton(
            backgroundColor = backgroundColor,
            onClick = onClick,
            content = content
        )

        if (indicatorValue != null) {
            Box(
                modifier = Modifier
                    .circleLayout(
                        size = 20.dp,
                        background = Color.Red
                    )
                    .align(Alignment.TopEnd),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = indicatorValue,
                    fontSize = 12.sp,
                    lineHeight = 12.sp,
                    color = White,
                    style = LocalTextStyle.current.copy(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
            }
        }
    }
}

@Preview
@Composable
private fun IndicatorRoundedIconButtonPreview() {
    IndicatorRoundedIconButton(
        indicatorValue = "11",
        onClick = {}
    ) {
        Icon(
            imageVector = Icons.Outlined.ShoppingCart,
            contentDescription = null,
            tint = Color.Magenta
        )
    }
}
