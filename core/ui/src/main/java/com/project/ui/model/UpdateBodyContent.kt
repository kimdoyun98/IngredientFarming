package com.project.ui.model

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.Color
import com.project.ui.IconResource

data class UpdateBodyContent(
    @StringRes val title: Int,
    val content: String,
    val background: Color,
    val iconResource: IconResource,
    val iconTint: Color,
    @StringRes val iconContentDescription: Int
)
