package com.project.ingredient

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.project.designsystem.theme.IngredientFarmingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val barcodeViewModel: BarcodeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        barcodeViewModel.setBarcode("8801097250079")

        setContent {
            IngredientFarmingTheme {
                Text("Test Ingredient Module")
            }
        }
    }
}
