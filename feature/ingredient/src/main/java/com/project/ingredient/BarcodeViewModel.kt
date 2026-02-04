package com.project.ingredient

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.ingredient.usecase.GetBarcodeInfoUseCase
import com.project.model.barcode.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retryWhen
import javax.inject.Inject

@HiltViewModel
class BarcodeViewModel @Inject constructor(
    barcodeInfoUseCase: GetBarcodeInfoUseCase
): ViewModel() {
    private val _barcode: MutableStateFlow<String?> = MutableStateFlow(null)
    private val barcode: StateFlow<String?> = _barcode.asStateFlow()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    init {
        barcode
            .filterNotNull()
            .onEach { barcode ->
                _products.value = barcodeInfoUseCase.invoke(barcode)
            }
            .retryWhen { cause, attempt ->
                when (cause) {
                    is Exception -> {
                        Log.e(
                            "RetryWhen",
                            "Exception occurred: ${cause.message}. Retrying... Attempt: $attempt"
                        )
                        attempt <= 5
                    }

                    else -> false
                }
            }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    fun setBarcode(barcode: String) {
        _barcode.value = barcode
    }
}
