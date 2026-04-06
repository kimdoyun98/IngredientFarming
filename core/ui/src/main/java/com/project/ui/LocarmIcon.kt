package com.project.ui

import androidx.annotation.DrawableRes
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource

@Composable
fun LocarmIcon(
    modifier: Modifier = Modifier,
    iconResource: IconResource,
    contentDescription: String?,
    tint: Color = LocalContentColor.current
) {
    when (iconResource) {
        is IconResource.PainterIcon -> {
            Icon(
                modifier = modifier,
                painter = painterResource(iconResource.id),
                contentDescription = contentDescription,
                tint = tint
            )
        }

        is IconResource.ImageVectorIcon -> {
            Icon(
                modifier = modifier,
                imageVector = iconResource.resource,
                contentDescription = contentDescription,
                tint = tint
            )
        }
    }
}

sealed interface IconResource {
    data class PainterIcon(@DrawableRes val id: Int) : IconResource

    data class ImageVectorIcon(val resource: ImageVector) : IconResource
}
