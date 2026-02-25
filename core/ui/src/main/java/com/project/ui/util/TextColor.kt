package com.project.ui.util

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

fun getLuminanceTextColor(background: Color) = if (background.luminance() > 0.5f) Color.Black else Color.White
