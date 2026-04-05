package com.project.ingredient_manage.update

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.WatchLater
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.designsystem.compose.IngredientFarmingButton
import com.project.designsystem.theme.Blue
import com.project.designsystem.theme.DeepOrange
import com.project.designsystem.theme.Green
import com.project.designsystem.theme.MoreLightBlue
import com.project.designsystem.theme.MoreLightGreen
import com.project.designsystem.theme.MoreLightOrange
import com.project.designsystem.theme.Purple40
import com.project.designsystem.theme.Purple80
import com.project.ingredient_manage.contract.update.UpdateIntent
import com.project.ingredient_manage.contract.update.UpdateState
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore
import com.project.ui.AppBarType
import com.project.ui.CategoryLargeIconBox
import com.project.ui.IconBoxSize
import com.project.ui.IngredientFarmingTopAppBar
import com.project.ui.MediumIconBox
import com.project.ui.R
import com.project.ui.modifier.singleClickEvent
import com.project.ui.util.getLocalDateText
import java.time.LocalDate

@Composable
internal fun UpdateHoldIngredientScreen(
    modifier: Modifier = Modifier,
    updateState: UpdateState,
    onIntent: (UpdateIntent) -> Unit,
) {
    UpdateHoldIngredientScreen(
        modifier = modifier,
        name = updateState.name,
        count = updateState.count,
        category = updateState.category,
        store = updateState.store,
        enterDate = updateState.enterDate,
        expirationDate = updateState.expirationDate,
        onTopAppBarNavigationClick = { onIntent(UpdateIntent.OnTopAppBarNavigationClick) },
        onCountMinusButtonClick = { onIntent(UpdateIntent.OnCountMinusButtonClick) },
        onDeleteButtonClick = { onIntent(UpdateIntent.OnDeleteButtonClick) },
        onUpdateButtonClick = { onIntent(UpdateIntent.OnUpdateButtonClick) }
    )
}

@Composable
internal fun UpdateHoldIngredientScreen(
    modifier: Modifier = Modifier,
    name: String,
    count: Int,
    category: IngredientCategory,
    store: IngredientStore,
    enterDate: LocalDate,
    expirationDate: LocalDate,
    onTopAppBarNavigationClick: () -> Unit,
    onCountMinusButtonClick: () -> Unit,
    onDeleteButtonClick: () -> Unit,
    onUpdateButtonClick: () -> Unit,
) {
    IngredientFarmingTopAppBar(
        modifier = modifier,
        title = stringResource(com.project.ingredient_manage.R.string.update_hold_screen_title),
        type = AppBarType.Navigation,
        onClickNavigation = onTopAppBarNavigationClick
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            Column(
                Modifier
                    .padding(32.dp)
            ) {
                HeadContent(
                    name = name,
                    category = category,
                )

                BodyContent(
                    count = count,
                    store = store,
                    enterDate = enterDate,
                    expirationDate = expirationDate,
                    onCountMinusButtonClick = onCountMinusButtonClick,
                )
            }

            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
            ) {
                singleClickEvent { singleEvent ->
                    IngredientFarmingButton(
                        modifier = Modifier
                            .weight(1f),
                        background = Red,
                        onClick = {
                            singleEvent.event {
                                onDeleteButtonClick()
                            }
                        }
                    ) { Text(stringResource(com.project.ingredient_manage.R.string.delete_button_text)) }

                    IngredientFarmingButton(
                        modifier = Modifier
                            .weight(1f),
                        onClick = {
                            singleEvent.event {
                                onUpdateButtonClick()
                            }
                        }
                    ) { Text(stringResource(com.project.ingredient_manage.R.string.update_button_text)) }
                }
            }
        }
    }
}

@Composable
private fun HeadContent(
    modifier: Modifier = Modifier,
    name: String,
    category: IngredientCategory,
) {
    Row(
        modifier = modifier
            .padding(bottom = 32.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        CategoryLargeIconBox(category = category)

        Column {
            Text(
                text = name,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = category.n,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
private fun BodyContent(
    modifier: Modifier = Modifier,
    count: Int,
    store: IngredientStore,
    enterDate: LocalDate,
    expirationDate: LocalDate,
    onCountMinusButtonClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        CountContent(
            count = count,
            onCountMinusButtonClick = onCountMinusButtonClick
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 8.dp),
            thickness = 0.5.dp,
            color = Color.LightGray
        )

        BodyContentItem(
            itemTitle = stringResource(R.string.store_type),
            itemContent = store.n,
            iconBackGroundColor = MoreLightBlue
        ) {
            Icon(
                modifier = Modifier
                    .size(IconBoxSize.MEDIUM.iconSize.dp),
                painter = painterResource(id = R.drawable.ic_refrigerator),
                contentDescription = stringResource(com.project.ingredient_manage.R.string.store_icon_description),
                tint = Blue
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 8.dp),
            thickness = 0.5.dp,
            color = Color.LightGray
        )

        BodyContentItem(
            itemTitle = stringResource(R.string.expiration_date),
            itemContent = getLocalDateText(expirationDate),
            iconBackGroundColor = Purple80
        ) {
            Icon(
                modifier = Modifier
                    .size(IconBoxSize.MEDIUM.iconSize.dp),
                imageVector = Icons.Outlined.CalendarToday,
                contentDescription = stringResource(com.project.ingredient_manage.R.string.expiration_date_icon_description),
                tint = Purple40
            )
        }

        HorizontalDivider(
            modifier = Modifier
                .padding(vertical = 8.dp),
            thickness = 0.5.dp,
            color = Color.LightGray
        )

        BodyContentItem(
            itemTitle = stringResource(R.string.enter_date),
            itemContent = enterDate.toString(),
            iconBackGroundColor = MoreLightOrange
        ) {
            Icon(
                modifier = Modifier
                    .size(IconBoxSize.MEDIUM.iconSize.dp),
                imageVector = Icons.Outlined.WatchLater,
                contentDescription = stringResource(com.project.ingredient_manage.R.string.store_icon_description),
                tint = DeepOrange
            )
        }
    }
}

@Composable
private fun CountContent(
    modifier: Modifier = Modifier,
    count: Int,
    onCountMinusButtonClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        BodyContentItem(
            itemTitle = stringResource(R.string.count),
            itemContent = "$count",
            iconBackGroundColor = MoreLightGreen
        ) {
            Icon(
                modifier = Modifier
                    .size(IconBoxSize.MEDIUM.iconSize.dp),
                painter = painterResource(id = R.drawable.ic_3d_box),
                contentDescription = stringResource(com.project.ingredient_manage.R.string.count_icon_description),
                tint = Green
            )
        }

        IconButton(
            onClick = onCountMinusButtonClick
        ) {
            Icon(
                imageVector = Icons.Default.Remove,
                contentDescription = stringResource(com.project.ingredient_manage.R.string.count_minus_button_description),
            )
        }
    }
}

@Composable
private fun BodyContentItem(
    modifier: Modifier = Modifier,
    itemTitle: String,
    itemContent: String,
    iconBackGroundColor: Color,
    icon: @Composable () -> Unit,
) {
    Row(
        modifier = modifier
            .height(IconBoxSize.MEDIUM.boxSize.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        MediumIconBox(
            iconBackgroundColor = iconBackGroundColor,
            icon = icon
        )

        Column(
            modifier = Modifier
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = itemTitle,
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Gray
            )

            Text(
                text = itemContent,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
private fun UpdateHoldIngredientScreenPreview() {
    UpdateHoldIngredientScreen(
        updateState = UpdateState(
            name = "삼겹살",
            count = 4,
            category = IngredientCategory.MEAT,
            store = IngredientStore.FROZEN,
            expirationDate = LocalDate.parse("9999-01-01"),//LocalDate.now().plusDays(1),
            enterDate = LocalDate.now()
        ),
        onIntent = {}
    )
}
