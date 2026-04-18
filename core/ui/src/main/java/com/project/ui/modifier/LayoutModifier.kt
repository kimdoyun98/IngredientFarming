package com.project.ui.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp

fun Modifier.shadowLayout(
    elevation: Int = 2
) = this
    .shadow(
        elevation = elevation.dp,
        shape = RoundedCornerShape(16.dp),
        clip = false
    )
    .background(
        color = White,
        shape = RoundedCornerShape(16.dp)
    )

fun Modifier.dottedLineLayout(color: Color = LightGray) = this
    .drawBehind {
        val strokeWidth = 1.dp.toPx()
        val dashWidth = 6.dp.toPx()
        val gapWidth = 4.dp.toPx()

        drawRoundRect(
            color = color,
            size = size,
            style = Stroke(
                width = strokeWidth,
                pathEffect = PathEffect.dashPathEffect(
                    floatArrayOf(dashWidth, gapWidth)
                )
            ),
            cornerRadius = CornerRadius(16.dp.toPx())
        )
    }
