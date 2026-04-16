package com.project.ui.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.project.designsystem.theme.Blue
import com.project.designsystem.theme.DeepOrange
import com.project.designsystem.theme.MoreLightBlue
import com.project.designsystem.theme.MoreLightOrange
import com.project.designsystem.theme.Purple40
import com.project.designsystem.theme.Purple80
import com.project.model.ingredient.Ingredient
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore
import com.project.ui.IconResource
import com.project.ui.R
import com.project.ui.model.CardContent
import com.project.ui.model.UpdateBodyContent
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import java.time.LocalDate

@Composable
fun rememberIngredientCategoryTitles() = remember {
    IngredientCategory.entries.toList().map { it.title }.toImmutableList()
}

@Composable
fun rememberIngredientStoreTitles() = remember {
    IngredientStore.entries.toList().map { it.title }.toImmutableList()
}

@Composable
fun rememberCardContentList(item: Ingredient): ImmutableList<CardContent> {
    val expirationDate = getLocalDateText(item.expirationDate)

    return remember(item) {
        persistentListOf(
            CardContent(
                iconResource = IconResource.PainterIcon(id = R.drawable.ic_3d_box),
                title = R.string.count,
                content = item.count.format()
            ),
            CardContent(
                iconResource = IconResource.PainterIcon(id = R.drawable.ic_refrigerator),
                title = R.string.store_type,
                content = item.store.title
            ),
            CardContent(
                iconResource = IconResource.ImageVectorIcon(resource = Icons.Default.CalendarToday),
                title = R.string.expiration_date,
                content = expirationDate
            )
        )
    }
}

@Composable
fun rememberUpdateContentList(
    store: IngredientStore,
    expirationDate: LocalDate,
    enterDate: LocalDate,
): ImmutableList<UpdateBodyContent> {
    val expirationDateText = getLocalDateText(expirationDate)

    return remember {
        persistentListOf(
            UpdateBodyContent(
                title = R.string.store_type,
                content = store.title,
                background = MoreLightBlue,
                iconResource = IconResource.PainterIcon(id = R.drawable.ic_refrigerator),
                iconContentDescription = R.string.store_icon_description,
                iconTint = Blue
            ),

            UpdateBodyContent(
                title = R.string.expiration_date,
                content = expirationDateText,
                background = Purple80,
                iconResource = IconResource.ImageVectorIcon(resource = Icons.Outlined.CalendarToday),
                iconContentDescription = R.string.expiration_date_icon_description,
                iconTint = Purple40
            ),

            UpdateBodyContent(
                title = R.string.enter_date,
                content = enterDate.toString(),
                background = MoreLightOrange,
                iconResource = IconResource.ImageVectorIcon(resource = Icons.Outlined.WatchLater),
                iconContentDescription = R.string.enter_date_icon_description,
                iconTint = DeepOrange
            ),
        )
    }
}
