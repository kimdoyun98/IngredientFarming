package com.project.ingredient_manage.defaultingredient

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.project.ingredient.usecase.manage.GetDefaultIngredientsUseCase
import com.project.ingredient_manage.defaultingredient.contract.DefaultIngredientEffect
import com.project.ingredient_manage.defaultingredient.contract.DefaultIngredientState
import com.project.model.ingredient.IngredientFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class DefaultIngredientManageViewModel @Inject constructor(
    private val getDefaultIngredientsUseCase: GetDefaultIngredientsUseCase,
) : ContainerHost<DefaultIngredientState, DefaultIngredientEffect>, ViewModel() {
    override val container =
        container<DefaultIngredientState, DefaultIngredientEffect>(DefaultIngredientState())

    private val queryFlow = container.stateFlow.map { it.query }
    private val categoryFlow = container.stateFlow.map { it.selectedCategory }

    @OptIn(FlowPreview::class)
    private val filterFlow: Flow<IngredientFilter> =
        combine(queryFlow, categoryFlow) { query, category ->
            IngredientFilter(
                query = query,
                category = category
            )
        }
            .debounce(300)
            .distinctUntilChanged()

    @OptIn(FlowPreview::class)
    val ingredients = filterFlow
        .debounce(300L)
        .distinctUntilChanged()
        .flatMapLatest { filter ->
            getDefaultIngredientsUseCase.invoke(filter)
        }
        .cachedIn(viewModelScope)

}
