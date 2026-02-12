package com.project.ingredient.barcode.ui.save

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.ingredient.barcode.contract.save.SaveIngredientEffect
import com.project.ingredient.barcode.contract.save.SaveIngredientIntent
import com.project.ingredient.barcode.contract.save.SaveIngredientState
import com.project.ingredient.usecase.InsertIngredientUseCase
import com.project.model.barcode.Ingredient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SaveIngredientViewModel @Inject constructor(
    private val insertIngredientUseCase: InsertIngredientUseCase,
) : ContainerHost<SaveIngredientState, SaveIngredientEffect>, ViewModel() {
    override val container =
        container<SaveIngredientState, SaveIngredientEffect>(SaveIngredientState())

    private val _newIngredient = MutableStateFlow<Ingredient>(Ingredient())
    private val newIngredient = _newIngredient.asStateFlow()

    init {
        newIngredient
            .filter { it.name != "알수없음" }
            .onEach {
                intent {
                    reduce {
                        val list = state.ingredientList.toMutableList()
                        list.add(it)

                        state.copy(ingredientList = list)
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: SaveIngredientIntent) {
        when (intent) {
            is SaveIngredientIntent.IngredientCountChange -> intent {

            }

            is SaveIngredientIntent.SaveButtonClick -> intent {

            }

            is SaveIngredientIntent.DirectInputButtonClick -> intent {
                postSideEffect(SaveIngredientEffect.NavigateToDirectInputScreen)
            }
        }
    }

    fun addNewIngredient(igd: Ingredient) {
        _newIngredient.value = igd
    }

    override fun onCleared() {
        Log.e("SaveIngredientViewModel", "onCleared")
        super.onCleared()
    }
}
