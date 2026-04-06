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
    get() = when(this){
        IngredientCategory.CONDIMENT -> {
            IconResource.IsImageVector(Icons.Outlined.PropaneTank)
        }

        IngredientCategory.VEGETABLE -> {
            IconResource.IsPainter(id = R.drawable.ic_vegetable)
        }

        IngredientCategory.FRUIT -> {
            IconResource.IsPainter(id = R.drawable.ic_apple)
        }

        IngredientCategory.MEAT -> {
            IconResource.IsImageVector(Icons.Outlined.EggAlt)
        }

        IngredientCategory.DAIRY -> {
            IconResource.IsPainter(id = R.drawable.ic_milk)
        }

        IngredientCategory.GRAIN -> {
            IconResource.IsImageVector(Icons.Outlined.Spa)
        }

        IngredientCategory.BEVERAGE -> {
            IconResource.IsImageVector(Icons.Outlined.LocalCafe)
        }

        IngredientCategory.SNACK -> {
            IconResource.IsImageVector(Icons.Outlined.Cookie)
        }

        IngredientCategory.OTHER -> {
            IconResource.IsImageVector(Icons.Default.MoreHoriz)
        }
    }
