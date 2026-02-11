package com.project.ingredient.barcode.ui.save

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.ingredient.usecase.InsertIngredientUseCase
import com.project.model.barcode.Ingredient
import com.project.model.barcode.IngredientCategory
import com.project.model.barcode.IngredientStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SaveIngredientViewModel @Inject constructor(
    private val insertIngredientUseCase: InsertIngredientUseCase,
): ViewModel(){
    private val testData = listOf(
        Ingredient(
            name = "Tomato",
            count = 1,
            category = IngredientCategory.VEGETABLE,
            enterDate = LocalDate.now(),
            expirationDate = LocalDate.now().plusDays(7),
            store = IngredientStore.REFRIGERATED
        ),
        Ingredient(
            name = "Test",
            count = 2,
            category = IngredientCategory.CONDIMENT,
            enterDate = LocalDate.now(),
            expirationDate = LocalDate.now().plusDays(7),
            store = IngredientStore.ROOM_TEMPERATURE
        )
    )

    fun saveTestData() {
        viewModelScope.launch {
            insertIngredientUseCase.invoke(testData)
        }
    }
}
