package com.project.recipe.recipelist.component

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.placeholder
import com.project.designsystem.theme.Green
import com.project.model.recipe.RecipeCategory
import com.project.recipe.R
import com.project.ui.IconResource
import com.project.ui.LocarmIcon
import com.project.ui.modifier.shadowLayout
import com.project.ui.modifier.singleClickable

@Composable
internal fun RecipeCardItem(
    modifier: Modifier = Modifier,
    imageUri: String,
    category: RecipeCategory,
    name: String,
    time: Int,
    people: Int,
    totalIngredient: Int,
    holdIngredient: Int,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .shadowLayout()
            .singleClickable(onClick),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Column {
            FoodImageBox(
                imageUri = imageUri,
                category = category
            )

            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                text = name,
                style = MaterialTheme.typography.titleMedium
            )

            RecipeInfoContent(
                time = time,
                people = people
            )

            IngredientMatchProgress(
                totalIngredient = totalIngredient,
                holdIngredient = holdIngredient,
            )
        }
    }
}

@Composable
private fun FoodImageBox(
    imageUri: String,
    category: RecipeCategory,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
    ) {

        AsyncImage(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentSize(Alignment.Center)
                .aspectRatio(1f)
                .clip(MaterialTheme.shapes.medium),
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUri)
                .placeholder(R.drawable.ic_default_image)
                .build(),
            contentDescription = stringResource(R.string.add_recipe_photo_description),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            clipToBounds = true,
            fallback = painterResource(R.drawable.ic_default_image)
        )

        SuggestionChip(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 12.dp),
            label = {
                Text(
                    text = category.title,
                    style = MaterialTheme.typography.labelMedium
                )
            },
            onClick = {}
        )
    }
}

@Composable
private fun RecipeInfoContent(
    time: Int,
    people: Int,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RecipeInfoContentItem(
            iconResource = IconResource.ImageVectorIcon(Icons.Default.AccessTime),
            title = "${time}분"
        )

        Spacer(modifier = Modifier.width(8.dp))

        RecipeInfoContentItem(
            iconResource = IconResource.PainterIcon(com.project.ui.R.drawable.ic_chef_hat),
            title = "${people}인분"
        )
    }
}

@Composable
private fun RecipeInfoContentItem(
    iconResource: IconResource,
    title: String,
) {
    LocarmIcon(
        modifier = Modifier
            .size(16.dp),
        iconResource = iconResource,
        contentDescription = "",
        tint = Gray
    )

    Spacer(modifier = Modifier.width(4.dp))

    Text(
        text = title,
        style = MaterialTheme.typography.bodySmall,
        color = Gray
    )
}

@Composable
private fun IngredientMatchProgress(
    totalIngredient: Int,
    holdIngredient: Int,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "재료 매칭률",
            style = MaterialTheme.typography.bodySmall,
            color = Gray,
        )

        Text(
            text = "${holdIngredient}/${totalIngredient}개",
            style = MaterialTheme.typography.bodySmall,
            color = Gray,
        )
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp)
            .height(8.dp)
            .clip(CircleShape)
            .background(LightGray)
    ) {
        LinearProgressIndicator(
            progress = { holdIngredient / totalIngredient.toFloat() },
            modifier = Modifier
                .fillMaxSize(),
            color = Green,
            trackColor = LightGray,
            strokeCap = StrokeCap.Round
        )
    }
}

@Preview
@Composable
private fun RecipeCardItemPreview() {
    RecipeCardItem(
        imageUri = "",
        category = RecipeCategory.CHINESE_FOOD,
        name = "비빔밥",
        time = 30,
        people = 2,
        totalIngredient = 10,
        holdIngredient = 4,
        onClick = {}
    )
}
