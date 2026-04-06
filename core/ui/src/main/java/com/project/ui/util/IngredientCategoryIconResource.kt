package com.project.ui.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.outlined.Cookie
import androidx.compose.material.icons.outlined.EggAlt
import androidx.compose.material.icons.outlined.LocalCafe
import androidx.compose.material.icons.outlined.PropaneTank
import androidx.compose.material.icons.outlined.Spa
import com.project.model.ingredient.IngredientCategory
import com.project.ui.IconResource
import com.project.ui.R

val IngredientCategory.iconResource: IconResource
    get() = when (this) {
        IngredientCategory.CONDIMENT -> {
            IconResource.ImageVectorIcon(Icons.Outlined.PropaneTank)
        }

        IngredientCategory.VEGETABLE -> {
            IconResource.PainterIcon(id = R.drawable.ic_vegetable)
        }

        IngredientCategory.FRUIT -> {
            IconResource.PainterIcon(id = R.drawable.ic_apple)
        }

        IngredientCategory.MEAT -> {
            IconResource.ImageVectorIcon(Icons.Outlined.EggAlt)
        }

        IngredientCategory.DAIRY -> {
            IconResource.PainterIcon(id = R.drawable.ic_milk)
        }

        IngredientCategory.GRAIN -> {
            IconResource.ImageVectorIcon(Icons.Outlined.Spa)
        }

        IngredientCategory.BEVERAGE -> {
            IconResource.ImageVectorIcon(Icons.Outlined.LocalCafe)
        }

        IngredientCategory.SNACK -> {
            IconResource.ImageVectorIcon(Icons.Outlined.Cookie)
        }

        IngredientCategory.OTHER -> {
            IconResource.ImageVectorIcon(Icons.Default.MoreHoriz)
        }
    }
