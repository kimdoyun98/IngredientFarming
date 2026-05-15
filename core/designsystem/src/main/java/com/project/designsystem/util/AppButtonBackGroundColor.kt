package com.project.designsystem.util

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Stable
sealed interface AppButtonBackGroundColor {
    @Stable
    data class BackGroundBrush(val brush: Brush) : AppButtonBackGroundColor

    @Stable
    data class BackGroundColor(val color: Color) : AppButtonBackGroundColor
}
