package com.project.designsystem.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.project.designsystem.util.AppButtonBackGroundColor
import com.project.ui_core.modifier.singleClickEvent
import com.project.ui_core.modifier.singleClickable

@Composable
fun AppPositiveButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    enabled: Boolean = true,
    contentPadding: Dp = 14.dp,
    onClick: () -> Unit,
) {
    AppButton(
        modifier = modifier,
        text = text,
        textStyle = textStyle,
        backgroundColor = AppButtonBackGroundColor.BackGroundBrush(
            Brush.horizontalGradient(
                colors = listOf(
                    Color(0xFF059669),
                    Color(0xFF10B981)
                )
            )
        ),
        enabled = enabled,
        contentPadding = contentPadding,
        onClick = onClick
    )
}

@Composable
fun AppNegativeButton(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    contentPadding: Dp = 14.dp,
    onClick: () -> Unit,
) {
    AppButton(
        modifier = modifier,
        text = text,
        textStyle = textStyle,
        textColor = DarkGray,
        backgroundColor = AppButtonBackGroundColor.BackGroundColor(Color(0xFFD1D5DB)),
        contentPadding = contentPadding,
        onClick = onClick
    )
}

@Composable
private fun AppButton(
    modifier: Modifier = Modifier,
    text: String,
    textColor: Color = Color.White,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    backgroundColor: AppButtonBackGroundColor,
    enabled: Boolean = true,
    contentPadding: Dp = 14.dp,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.97f else 1f,
        label = "button_scale"
    )
    val alpha by animateFloatAsState(
        targetValue = if (isPressed) 0.85f else 1f,
        label = "button_alpha"
    )
    val backgroundModifier = when {
        !enabled -> {
            Modifier.background(Gray)
        }

        backgroundColor is AppButtonBackGroundColor.BackGroundBrush -> {
            Modifier.background(backgroundColor.brush)
        }

        backgroundColor is AppButtonBackGroundColor.BackGroundColor -> {
            Modifier.background(backgroundColor.color)
        }

        else -> Modifier
    }

    Box(
        modifier = modifier
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                this.alpha = alpha
            }
            .clip(RoundedCornerShape(12.dp))
            .then(backgroundModifier)
            .singleClickable(
                interactionSource = interactionSource,
                enabled = enabled,
                onClick = onClick
            )
            .padding(vertical = contentPadding),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            style = textStyle
        )
    }
}

@Composable
fun IngredientFarmingWideButton(
    modifier: Modifier = Modifier,
    background: Color? = null,
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(8.dp),
        colors =
            if (background == null) ButtonDefaults.buttonColors()
            else ButtonDefaults.buttonColors(containerColor = background),
        onClick = { onClick() },
        enabled = enabled,
        content = { content() }
    )
}

@Composable
fun IngredientFarmingButton(
    modifier: Modifier = Modifier,
    background: Color? = null,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Button(
        modifier = modifier
            .padding(4.dp),
        colors =
            if (background == null) ButtonDefaults.buttonColors()
            else ButtonDefaults.buttonColors(containerColor = background),
        contentPadding = contentPadding,
        shape = RoundedCornerShape(8.dp),
        onClick = { onClick() },
        enabled = enabled,
        content = { content() }
    )
}

@Composable
fun AppDefaultButton(
    modifier: Modifier = Modifier,
    background: Color? = null,
    enabled: Boolean = true,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    onClick: () -> Unit,
    content: @Composable () -> Unit
){
    singleClickEvent { singleEvent ->
        Button(
            modifier = modifier,
            colors =
                if (background == null) ButtonDefaults.buttonColors()
                else ButtonDefaults.buttonColors(containerColor = background),
            contentPadding = contentPadding,
            shape = RoundedCornerShape(8.dp),
            onClick = {
                singleEvent.event {
                    onClick()
                }
            },
            enabled = enabled,
            content = { content() }
        )
    }
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

@Preview
@Composable
private fun AppPositiveButtonPreview() {
    AppPositiveButton(
        modifier = Modifier.fillMaxWidth(),
        text = "확인"
    ) { }
}

@Preview
@Composable
private fun DisabledAppPositiveButtonPreview() {
    AppPositiveButton(
        modifier = Modifier.fillMaxWidth(),
        enabled = false,
        text = "확인"
    ) { }
}

@Preview
@Composable
private fun AppNegativeButtonPreview() {
    AppNegativeButton(
        modifier = Modifier.fillMaxWidth(),
        text = "취소",
    ) {}
}
