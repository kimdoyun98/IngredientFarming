package com.project.shopping_cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.ingredient.usecase.GetIngredientUseCase
import com.project.ingredient.usecase.shopping.DeleteShoppingCartItemUseCase
import com.project.ingredient.usecase.shopping.GetAllShoppingCartItemsUseCase
import com.project.ingredient.usecase.shopping.InsertShoppingCartItemUseCase
import com.project.ingredient.usecase.shopping.SaveCartSuccessItemsUseCase
import com.project.model.cart.ShoppingCartUiModel
import com.project.model.cart.asShoppingCart
import com.project.model.ingredient.getIndexByIngredientCategory
import com.project.model.ingredient.getIndexToIngredientCategory
import com.project.shopping_cart.contract.ShoppingCartEffect
import com.project.shopping_cart.contract.ShoppingCartIntent
import com.project.shopping_cart.contract.ShoppingCartState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
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

    init {
        intent {
            getAllShoppingCartItemsUseCase.invoke()
                .collectLatest { shoppingCarts ->
                    reduce { state.copy(cartList = shoppingCarts.toImmutableList()) }
                }
        }

        viewModelScope.launch {
            container.stateFlow
                .map { it.addItemNameQuery }
                .distinctUntilChanged()
                .filter { it.isNotBlank() }
                .debounce(300L)
                .collectLatest {
                    onIntent(ShoppingCartIntent.SearchIngredient(it))
                }
        }
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
                reduce { state.copy(addItemNameQuery = intent.name) }
            }

            is ShoppingCartIntent.OnAddItemCountChanged -> intent {
                reduce { state.copy(addItemCount = intent.count) }
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
                        addItemCategorySelected = -1,
                        addItemIngredientId = -1
                    )
                }
            }

            is ShoppingCartIntent.OnItemAddButtonClick -> intent {
                insertShoppingCartItemUseCase.invoke(
                    ShoppingCartUiModel(
                        ingredientId = state.addItemIngredientId,
                        name = state.addItemNameQuery,
                        count = state.addItemCount.toDouble(),
                        category = getIndexToIngredientCategory(state.addItemCategorySelected),
                        success = false
                    )
                )

                reduce {
                    state.copy(
                        addState = false,
                        addItemCount = "0",
                        addItemNameQuery = "",
                        addItemCategorySelected = -1,
                        addItemIngredientId = -1
                    )
                }
            }

            is ShoppingCartIntent.OnItemCheckBoxClick -> intent {
                val cartList = state.cartList.toMutableList()
                val changeItem = cartList[intent.index]
                cartList[intent.index] = changeItem.copy(success = !changeItem.success)

                reduce {
                    state.copy(
                        cartList = cartList.toImmutableList(),
                        saveSuccessItemState = cartList.any { it.success }
                    )
                }
            }

            is ShoppingCartIntent.OnItemDeleteClick -> intent {
                deleteShoppingCartItemUseCase.invoke(state.cartList[intent.index].asShoppingCart())
            }

            is ShoppingCartIntent.OnSaveCartItemsButtonClick -> intent {
                val successList = state.cartList.filter { it.success }
                saveCartSuccessItemsUseCase.invoke(successList)

                successList.forEach {
                    deleteShoppingCartItemUseCase.invoke(it.asShoppingCart())
                }
            }

            is ShoppingCartIntent.SearchIngredient -> intent {
                val ingredient = getIngredientUseCase.invoke(intent.query)

                if (intent.query != state.addItemNameQuery) return@intent
                if (ingredient != null) {
                    reduce {
                        state.copy(
                            addItemCategorySelected = getIndexByIngredientCategory(
                                ingredient.category
                            ),
                            addItemIngredientId = ingredient.id
                        )
                    }
                }
            }
        }
    }
}
