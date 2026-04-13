package com.project.ingredient_manage.update

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.project.ingredient.usecase.manage.DeleteHoldIngredientUseCase
import com.project.ingredient.usecase.manage.GetHoldIngredientUseCase
import com.project.ingredient.usecase.manage.UpdateHoldIngredientCountUseCase
import com.project.ingredient_manage.contract.update.UpdateEffect
import com.project.ingredient_manage.contract.update.UpdateIntent
import com.project.ingredient_manage.contract.update.UpdateState
import com.project.navigation.IngredientRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class UpdateHoldIngredientViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getHoldIngredientUseCase: GetHoldIngredientUseCase,
    private val updateHoldIngredientCountUseCase: UpdateHoldIngredientCountUseCase,
    private val deleteHoldIngredientUseCase: DeleteHoldIngredientUseCase,
) : ContainerHost<UpdateState, UpdateEffect>, ViewModel() {
    override val container = container<UpdateState, UpdateEffect>(UpdateState())
    private val id = savedStateHandle.toRoute<IngredientRoute.UpdateHoldIngredient>().id

    init {
        viewModelScope.launch {
            val holdIngredient = getHoldIngredientUseCase.invoke(id)
            intent {
                reduce {
                    state.copy(
                        name = holdIngredient.name,
                        count = holdIngredient.count,
                        category = holdIngredient.category,
                        store = holdIngredient.store,
                        enterDate = holdIngredient.enterDate,
                        expirationDate = holdIngredient.expirationDate
                    )
                }
            }
        }
    }

    fun onIntent(intent: UpdateIntent) {
        when (intent) {
            is UpdateIntent.OnTopAppBarNavigationClick -> intent {
                postSideEffect(UpdateEffect.PopBackStack)
            }

            is UpdateIntent.OnCountMinusButtonClick -> intent {
                if (state.count == 1) return@intent

                reduce { state.copy(count = state.count - 1) }
            }

            is UpdateIntent.OnUpdateButtonClick -> intent {
                updateHoldIngredientCountUseCase.invoke(id, state.count)

                postSideEffect(UpdateEffect.PopBackStack)
            }

            is UpdateIntent.OnDeleteButtonClick -> intent {
                deleteHoldIngredientUseCase.invoke(listOf(id))

                postSideEffect(UpdateEffect.PopBackStack)
            }
        }
    }
}
