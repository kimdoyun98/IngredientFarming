package com.project.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun IngredientFarmingTopAppBar(
    modifier: Modifier = Modifier,
    type: AppBarType,
    title: String,
    actionIcon: @Composable (() -> Unit)? = null,
    onClickNavigation: () -> Unit = {},
    onClickAction: () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    if (type == AppBarType.All || type == AppBarType.Navigation) {
                        IconButton(
                            modifier = Modifier.semantics { testTag = "test" },
                            onClick = { onClickNavigation() }
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.top_bar_default_navigation_icon_description)
                            )
                        }
                    }
                },
                actions = {
                    if (type == AppBarType.All || type == AppBarType.Action) {
                        IconButton(
                            modifier = modifier,
                            onClick = { onClickAction() }
                        ) {
                            actionIcon?.invoke() ?: Icon(
                                imageVector = Icons.Default.Done,
                                contentDescription = stringResource(R.string.top_bar_default_action_icon_description)
                            )
                        }
                    }
                }
            )
        }
    ){  innerPadding ->
        content(innerPadding)
    }
}

enum class AppBarType {
    None, Navigation, Action, All
}
