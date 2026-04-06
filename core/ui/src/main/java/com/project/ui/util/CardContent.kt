package com.project.ui.util

import androidx.annotation.StringRes
import com.project.ui.IconResource

data class CardContent(
    val iconResource: IconResource,
    @StringRes val title: Int,
    val content: String,
)
