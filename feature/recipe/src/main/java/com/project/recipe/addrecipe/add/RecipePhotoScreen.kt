package com.project.recipe.addrecipe.add

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Image
import androidx.compose.material.icons.outlined.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import coil3.request.ImageRequest
import coil3.request.placeholder
import com.project.designsystem.compose.IngredientFarmingWideButton
import com.project.designsystem.theme.Green
import com.project.recipe.R
import com.project.recipe.addrecipe.component.MainTitleContent
import com.project.recipe.addrecipe.contract.AddRecipeIntent
import com.project.recipe.addrecipe.contract.AddRecipeState
import com.project.ui.IngredientFarmingCenterLoading
import com.project.ui.MediumIconBox
import com.project.ui.modifier.dottedLineLayout
import com.project.ui.modifier.singleClickable

@Composable
internal fun RecipePhotoScreen(
    modifier: Modifier = Modifier,
    state: AddRecipeState,
    onIntent: (AddRecipeIntent) -> Unit,
    launchMediaImagePermission: () -> Unit = {}
) {

    RecipePhotoScreen(
        modifier = modifier,
        photoUri = state.photo,
        onNextButtonClick = { onIntent(AddRecipeIntent.Photo.RecipePhotoNextButtonClick) },
        launchMediaImagePermission = launchMediaImagePermission,
    )
}

@Composable
internal fun RecipePhotoScreen(
    modifier: Modifier = Modifier,
    photoUri: Uri?, //TODO UnStable
    onNextButtonClick: () -> Unit,
    launchMediaImagePermission: () -> Unit,
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            MainTitleContent(
                title = stringResource(R.string.add_recipe_photo_main_title)
            )

            when (photoUri) {
                null -> {
                    InsertRecipePhoto(
                        onClick = launchMediaImagePermission
                    )
                }

                else -> {
                    RecipePhotoPreview(
                        uri = photoUri
                    )
                }
            }
        }

        IngredientFarmingWideButton(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            onClick = onNextButtonClick,
            background = Green
        ) {
            Text(stringResource(R.string.add_recipe_next_button_title))
        }
    }
}

@Composable
private fun RecipePhotoPreview(
    modifier: Modifier = Modifier,
    uri: Uri,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(170.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        SubcomposeAsyncImage(
            modifier = Modifier
                .wrapContentSize(Alignment.Center)
                .aspectRatio(1f)
                .weight(1f)
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(LocalContext.current)
                .data(uri)
                .placeholder(R.drawable.ic_default_image)
                .build(),
            contentDescription = stringResource(R.string.add_recipe_photo_description),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            clipToBounds = true
        ) {
            val state by painter.state.collectAsState()
            when (state) {
                is AsyncImagePainter.State.Loading -> IngredientFarmingCenterLoading()

                is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()

                is AsyncImagePainter.State.Empty -> Icon(
                    imageVector = Icons.Outlined.Image,
                    contentDescription = stringResource(R.string.add_recipe_photo_empty_photo_description)
                )

                is AsyncImagePainter.State.Error -> Icon(
                    imageVector = Icons.Outlined.ImageNotSupported,
                    contentDescription = stringResource(R.string.add_recipe_photo_error_photo_description)
                )
            }
        }
    }
}

@Composable
private fun InsertRecipePhoto(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(170.dp)
            .dottedLineLayout()
            .singleClickable(onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        MediumIconBox(
            iconBackgroundColor = Color(0xFFE8F5E9),
            icon = {
                Icon(
                    imageVector = Icons.Outlined.CameraAlt,
                    contentDescription = stringResource(R.string.add_recipe_photo_icon_description),
                    tint = Color(0xFF4CAF50)
                )
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.add_recipe_photo_add_title),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.add_recipe_photo_add_description),
            style = MaterialTheme.typography.bodySmall,
            color = Gray
        )
    }
}

@Preview(
    backgroundColor = 0xFFFFFF,
    showBackground = true
)
@Composable
private fun RecipePhotoScreenPreview() {
    RecipePhotoScreen(
        state = AddRecipeState(),
        onIntent = {},
        launchMediaImagePermission = {}
    )
}
