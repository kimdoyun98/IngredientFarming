package com.project.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.project.home.model.HomeStatusCardInfo
import com.project.home.model.rememberStatusCardInfo
import com.project.ui.StatusCard
import kotlinx.collections.immutable.ImmutableList

@Composable
internal fun StatusCardsContent(
    modifier: Modifier = Modifier,
    statusCardInfo: ImmutableList<HomeStatusCardInfo> = rememberStatusCardInfo(),
    ingredientCount: Int,
    expiresSoonCount: Int,
    recipeCount: Int,
) {
    LazyRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        items(
            items = statusCardInfo,
            key = { it.title }
        ) { info ->
            StatusCard(
                icon = info.icon,
                iconBackgroundColor = info.iconBackgroundColor,
                count = when (info.title) {
                    statusCardInfo[0].title -> ingredientCount
                    statusCardInfo[1].title -> expiresSoonCount
                    statusCardInfo[2].title -> recipeCount
                    else -> 0
                },
                title = info.title
            )
        }
    }
}

@Preview
@Composable
private fun StatusCardsContentPreview() {
    StatusCardsContent(
        ingredientCount = 5,
        expiresSoonCount = 2,
        recipeCount = 3
    )
}
