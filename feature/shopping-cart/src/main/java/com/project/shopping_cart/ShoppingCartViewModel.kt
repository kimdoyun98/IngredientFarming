package com.project.shopping_cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.ingredient.usecase.GetIngredientUseCase
import com.project.model.cart.ShoppingCart
import com.project.model.ingredient.getIndexByIngredientCategory
import com.project.model.ingredient.getIndexToIngredientCategory
import com.project.shopping_cart.contract.ShoppingCartEffect
import com.project.shopping_cart.contract.ShoppingCartIntent
import com.project.shopping_cart.contract.ShoppingCartState
import com.project.ingredient.usecase.shopping.DeleteShoppingCartItemUseCase
import com.project.ingredient.usecase.shopping.GetAllShoppingCartItemsUseCase
import com.project.ingredient.usecase.shopping.InsertShoppingCartItemUseCase
import com.project.ingredient.usecase.shopping.SaveCartSuccessItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class ShoppingCartViewModel @Inject constructor(
    private val getAllShoppingCartItemsUseCase: GetAllShoppingCartItemsUseCase,
    private val insertShoppingCartItemUseCase: InsertShoppingCartItemUseCase,
    private val deleteShoppingCartItemUseCase: DeleteShoppingCartItemUseCase,
    private val getIngredientUseCase: GetIngredientUseCase,
    private val saveCartSuccessItemsUseCase: SaveCartSuccessItemsUseCase,
) : ContainerHost<ShoppingCartState, ShoppingCartEffect>, ViewModel() {
    override val container = container<ShoppingCartState, ShoppingCartEffect>(ShoppingCartState())

    private val _nameQuery = MutableStateFlow("")
    private val nameQuery = _nameQuery.asStateFlow()

    private val _countQuery = MutableStateFlow("")
    private val countQuery = _countQuery.asStateFlow()

    init {
        getAllShoppingCartItemsUseCase.invoke()
            .onEach { shoppingCarts ->
                intent {
                    reduce { state.copy(cartList = shoppingCarts.toImmutableList()) }
                }
            }
            .launchIn(viewModelScope)

        nameQuery
            .onEach {
                intent {
                    reduce { state.copy(addItemNameQuery = it) }
                }
            }
            .debounce(300L)
            .onEach {
                val ingredient = getIngredientUseCase.invoke(it)
                if (ingredient != null) {
                    intent {
                        reduce {
                            state.copy(
                                addItemCategorySelected = getIndexByIngredientCategory(
                                    ingredient.category
                                )
                            )
                        }
                    }
                }
            }
            .launchIn(viewModelScope)

        countQuery
            .onEach {
                intent {
                    reduce { state.copy(addItemCount = it) }
                }
            }
            .launchIn(viewModelScope)

        container.stateFlow
            .onEach { state ->
                intent {
                    reduce { state.copy(saveSuccessItemState = state.cartList.any { it.success }) }
                }
            }
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: ShoppingCartIntent) {
        when (intent) {
            is ShoppingCartIntent.OnTopAppBarNavigationButtonClick -> intent {
                postSideEffect(ShoppingCartEffect.PopBackStack)
            }

            is ShoppingCartIntent.OnItemAddContentShowButtonClick -> intent {
                reduce { state.copy(addState = true) }
            }

            is ShoppingCartIntent.OnAddItemNameChanged -> intent {
                _nameQuery.value = intent.name
            }

            is ShoppingCartIntent.OnAddItemCountChanged -> intent {
                _countQuery.value = intent.count
            }

            is ShoppingCartIntent.OnAddItemCategoryChanged -> intent {
                if (intent.index == state.addItemCategorySelected) return@intent

                reduce { state.copy(addItemCategorySelected = intent.index) }
            }

            is ShoppingCartIntent.OnItemAddCancelButtonClick -> intent {
                reduce {
                    state.copy(
                        addState = false,
                        addItemCount = "0",
                        addItemNameQuery = "",
                        addItemCategorySelected = -1
                    )
                }
            }

            is ShoppingCartIntent.OnItemAddButtonClick -> intent {
                insertShoppingCartItemUseCase.invoke(
                    ShoppingCart(
                        name = state.addItemNameQuery,
                        count = state.addItemCount.toDouble(),
                        category = getIndexToIngredientCategory(state.addItemCategorySelected),
                        success = false
                    )
                )

                reduce { state.copy(addState = false) }
            }

            is ShoppingCartIntent.OnItemCheckBoxClick -> intent {
                val cartList = state.cartList.toMutableList()
                val changeItem = cartList[intent.index]
                cartList[intent.index] = changeItem.copy(success = !changeItem.success)

                reduce { state.copy(cartList = cartList.toImmutableList()) }
            }

            is ShoppingCartIntent.OnItemDeleteClick -> intent {
                deleteShoppingCartItemUseCase.invoke(state.cartList[intent.index])
            }

            is ShoppingCartIntent.OnSaveCartItemsButtonClick -> intent {
                val successList = state.cartList.filter { it.success }
                saveCartSuccessItemsUseCase.invoke(successList)

                successList.forEach {
                    deleteShoppingCartItemUseCase.invoke(it)
                }
            }
        }
    }
}
