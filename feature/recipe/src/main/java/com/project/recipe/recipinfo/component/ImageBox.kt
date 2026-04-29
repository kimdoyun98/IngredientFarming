package com.project.recipe.recipinfo.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.placeholder
import com.project.recipe.R
import java.io.File

@Composable
internal fun ImageBox(
    modifier: Modifier = Modifier,
    imagePath: String?,
) {
    Box(
        modifier = modifier,
    ) {
        AsyncImage(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentSize(Alignment.Center)
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(LocalContext.current)
                .data(imagePath?.let { File(imagePath) })
                .placeholder(R.drawable.ic_default_image)
                .build(),
            contentDescription = stringResource(R.string.add_recipe_photo_description),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            clipToBounds = true,
            fallback = painterResource(R.drawable.ic_default_image)
        )
    }
}
