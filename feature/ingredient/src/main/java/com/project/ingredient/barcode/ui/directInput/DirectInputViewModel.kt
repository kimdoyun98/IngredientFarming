package com.project.ingredient.barcode.ui.directInput

import androidx.lifecycle.ViewModel
import com.project.ingredient.barcode.contract.directInput.DirectInputEffect
import com.project.ingredient.barcode.contract.directInput.DirectInputIntent
import com.project.ingredient.barcode.contract.directInput.DirectInputState
import com.project.ui.util.RegexDate.regexDate
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container

class DirectInputViewModel : ContainerHost<DirectInputState, DirectInputEffect>, ViewModel() {
    override val container = container<DirectInputState, DirectInputEffect>(DirectInputState())


    fun onIntent(intent: DirectInputIntent) {
        when (intent) {
            is DirectInputIntent.TopAppBarNavigationClick -> intent {
                postSideEffect(DirectInputEffect.NavigateToBack)
            }

            is DirectInputIntent.StoreFilterChipSelect -> intent {
                reduce {
                    state.copy(
                        storeSelected = if (state.storeSelected == intent.selected) null else intent.selected
                    )
                }
            }

            is DirectInputIntent.CategoryFilterChipSelect -> intent {
                reduce {
                    state.copy(
                        categorySelected = if (state.categorySelected == intent.selected) null else intent.selected
                    )
                }
            }

            is DirectInputIntent.NextButtonClick -> intent {
                postSideEffect(DirectInputEffect.NavigateToSaveIngredient)
            }

            is DirectInputIntent.NameInputChange -> intent {
                reduce {
                    state.copy(
                        name = intent.name
                    )
                }
            }

            is DirectInputIntent.CountInputChange -> intent {
                reduce {
                    state.copy(count = intent.count)
                }
            }

            is DirectInputIntent.ExpirationDateInputChange -> intent {
                reduce {
                    state.copy(
                        expirationDate = regexDate(intent.expirationDate)
                    )
                }
            }
        }
    }
}
