package com.project.ingredient.barcode.ui.save

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.ingredient.barcode.contract.directInput.DirectInputState
import com.project.ingredient.barcode.contract.save.SaveIngredientEffect
import com.project.ingredient.barcode.contract.save.SaveIngredientIntent
import com.project.ingredient.barcode.contract.save.SaveIngredientState
import com.project.ingredient.usecase.InsertIngredientUseCase
import com.project.model.ingredient.Ingredient
import com.project.model.ingredient.IngredientCategory
import com.project.model.ingredient.IngredientStore
import com.project.model.ingredient.getIndexToIngredientCategory
import com.project.model.ingredient.getIndexToIngredientStore
import com.project.ui.util.RegexDate.regexDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class SaveIngredientViewModel @Inject constructor(
    private val insertIngredientUseCase: InsertIngredientUseCase,
) : ContainerHost<SaveIngredientState, SaveIngredientEffect>, ViewModel() {
    override val container =
        container<SaveIngredientState, SaveIngredientEffect>(SaveIngredientState())

    private val _newIngredient = MutableStateFlow<Ingredient>(Ingredient())
    private val newIngredient = _newIngredient.asStateFlow()

    private var updateIngredientIndex = -1

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
            is SaveIngredientIntent.SaveButtonClick -> intent {
                insertIngredientUseCase.invoke(state.ingredientList)

                postSideEffect(SaveIngredientEffect.SaveIngredient)
            }

            is SaveIngredientIntent.UpdateButtonClick -> intent {
                updateIngredientIndex = intent.index
                reduce {
                    state.copy(
                        updateIngredient = state.ingredientList[intent.index].asDirectInputState(),
                        openUpdateBottomSheetState = true
                    )
                }
            }

            is SaveIngredientIntent.PlusButtonClick -> intent {
                reduce { state.copy(openAddBottomSheetState = true) }
            }

            is SaveIngredientIntent.DirectInputButtonClick -> intent {
                postSideEffect(SaveIngredientEffect.NavigateToDirectInputScreen)
            }

            is SaveIngredientIntent.UpdateBottomSheetDisMiss -> intent {
                reduce { state.copy(openUpdateBottomSheetState = false) }
            }

            is SaveIngredientIntent.AddBottomSheetDisMiss -> intent {
                reduce { state.copy(openAddBottomSheetState = false) }
            }

        }
    }

    fun onBottomSheetIntent(intent: SaveIngredientIntent.BottomSheetIntent) {
        when (intent) {
            is SaveIngredientIntent.BottomSheetIntent.BarcodeScannerClick -> intent {
                reduce { state.copy(openAddBottomSheetState = false) }

                postSideEffect(SaveIngredientEffect.NavigateToBarcodeScannerScreen)
            }

            is SaveIngredientIntent.BottomSheetIntent.DirectInputClick -> intent {
                reduce { state.copy(openAddBottomSheetState = false) }

                postSideEffect(SaveIngredientEffect.NavigateToDirectInputScreen)
            }

            is SaveIngredientIntent.BottomSheetIntent.NameInputChange -> intent {
                reduce {
                    state.copy(updateIngredient = state.updateIngredient.copy(name = intent.name))
                }
            }

            is SaveIngredientIntent.BottomSheetIntent.CountInputChange -> intent {
                reduce {
                    state.copy(updateIngredient = state.updateIngredient.copy(count = intent.count))
                }
            }

            is SaveIngredientIntent.BottomSheetIntent.ExpirationDateInputChange -> intent {
                reduce {
                    state.copy(
                        updateIngredient = state.updateIngredient.copy(
                            expirationDate = regexDate(intent.expirationDate)
                        )
                    )
                }
            }

            is SaveIngredientIntent.BottomSheetIntent.StoreFilterChipSelect -> intent {
                reduce {
                    state.copy(
                        updateIngredient = state.updateIngredient.copy(
                            storeSelected = intent.selected
                        )
                    )
                }
            }

            is SaveIngredientIntent.BottomSheetIntent.CategoryFilterChipSelect -> intent {
                reduce {
                    state.copy(
                        updateIngredient = state.updateIngredient.copy(
                            categorySelected = intent.selected
                        )
                    )
                }
            }

            is SaveIngredientIntent.BottomSheetIntent.UpdateButtonClick -> intent {
                val igd = state.updateIngredient
                val changeList = state.ingredientList.toMutableList()
                changeList[updateIngredientIndex] = Ingredient(
                    name = igd.name,
                    count = igd.count.toInt(),
                    expirationDate = LocalDate.parse(igd.expirationDate),
                    store = getIndexToIngredientStore(igd.storeSelected),
                    category = getIndexToIngredientCategory(igd.categorySelected),
                )

                reduce {
                    state.copy(ingredientList = changeList, openUpdateBottomSheetState = false)
                }
            }
        }
    }

    fun addNewIngredient(igd: Ingredient) {
        _newIngredient.value = igd
    }

    private fun Ingredient.asDirectInputState() = DirectInputState(
        name = name,
        count = count.toString(),
        expirationDate = expirationDate.toString(),
        storeSelected = IngredientStore.entries.indexOf(store),
        categorySelected = IngredientCategory.entries.indexOf(category)
    )
}
