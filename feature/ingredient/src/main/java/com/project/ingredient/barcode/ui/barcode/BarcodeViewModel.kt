package com.project.ingredient.barcode.ui.barcode

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.ingredient.barcode.contract.barcode.BarcodeEffect
import com.project.ingredient.barcode.contract.barcode.BarcodeIntent
import com.project.ingredient.barcode.contract.barcode.BarcodeScanStatus
import com.project.ingredient.barcode.contract.barcode.BarcodeState
import com.project.ingredient.usecase.GetBarcodeInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retryWhen
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class BarcodeViewModel @Inject constructor(
    barcodeInfoUseCase: GetBarcodeInfoUseCase,
) : ContainerHost<BarcodeState, BarcodeEffect>, ViewModel() {
    override val container = container<BarcodeState, BarcodeEffect>(BarcodeState())
    private val _barcode: MutableStateFlow<String> = MutableStateFlow("")
    private val barcode: StateFlow<String> = _barcode.asStateFlow()

    init {
        barcode
            .filter { it.length == 13 }
            .debounce(500)
            .onEach { barcode ->
                intent { reduce { state.copy(scanStatus = BarcodeScanStatus.Scanning) } }
                val products = barcodeInfoUseCase.invoke(barcode)
                intent { reduce { state.copy(scanStatus = BarcodeScanStatus.Success(products)) } }
            }
            .retryWhen { cause, attempt ->
                when (cause) {
                    is Exception -> attempt <= 5
                    else -> false
                }
            }
            .catch {
                intent {
                    reduce { state.copy(scanStatus = BarcodeScanStatus.Error(it.message ?: "")) }
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun onIntent(intent: BarcodeIntent) {
        when (intent) {
            is BarcodeIntent.BarcodeScan -> {
                _barcode.value = intent.barcode
            }

            is BarcodeIntent.NavigateSaveScreen -> {
                intent {
                    postSideEffect(BarcodeEffect.NavigateSaveIngredientScreen)
                }
            }
        }
    }
}
