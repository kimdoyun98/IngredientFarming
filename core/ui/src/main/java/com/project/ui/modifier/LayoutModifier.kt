package com.project.ui.modifier

import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color.Companion.White
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
