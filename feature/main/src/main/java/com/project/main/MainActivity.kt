package com.project.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.remember
import com.project.designsystem.theme.IngredientFarmingTheme
import com.project.permission.IngredientFarmingPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val ingredientFarmingPermission = IngredientFarmingPermission(this)

        setContent {
            IngredientFarmingTheme {
                MainScreen(
                    ingredientFarmingPermission = remember { ingredientFarmingPermission }
                )
            }
        }
    }
}
