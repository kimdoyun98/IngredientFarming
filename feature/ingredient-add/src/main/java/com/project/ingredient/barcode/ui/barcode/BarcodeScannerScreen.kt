package com.project.ingredient.barcode.ui.barcode

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.project.ingredient.R
import com.project.ingredient.barcode.contract.barcode.BarcodeIntent
import com.project.ingredient.barcode.contract.barcode.BarcodeState
import com.project.ingredient.barcode.ui.barcode.util.BarcodeScanStatus
import com.project.model.barcode.Product
import com.project.ui.IngredientFarmingCenterLoading
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Composable
internal fun BarcodeScannerScreen(
    modifier: Modifier = Modifier,
    state: BarcodeState,
    onIntent: (BarcodeIntent) -> Unit,
    snackbarHostState: SnackbarHostState,
) {

    BarcodeScannerScreen(
        modifier = modifier,
        snackbarHostState = snackbarHostState,
        scanStatus = state.scanStatus,
        barcodeScan = { onIntent(BarcodeIntent.BarcodeScan(it)) },
        selectIngredient = { onIntent(BarcodeIntent.SelectIngredient(it)) },
        directInputClick = { onIntent(BarcodeIntent.DirectInputClick) },
        resetBarcodeScanStatus = { onIntent(BarcodeIntent.SnackBarDismissed) },
        barcodeResultEmpty = { onIntent(BarcodeIntent.BarcodeProductEmpty) },
        barcodeResultError = { onIntent(BarcodeIntent.BarcodeResultError) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BarcodeScannerScreen(
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState,
    scanStatus: BarcodeScanStatus,
    barcodeScan: (String) -> Unit,
    selectIngredient: (Product) -> Unit,
    directInputClick: () -> Unit,
    resetBarcodeScanStatus: () -> Unit,
    barcodeResultEmpty: () -> Unit,
    barcodeResultError: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { innerPadding ->
        CameraXScreen(
            modifier = modifier
                .padding(innerPadding),
            barcodeScan = { barcodeScan(it) },
        )

        when (val status = scanStatus) {
            is BarcodeScanStatus.Scanning -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    IngredientFarmingCenterLoading()
                }
            }

            is BarcodeScanStatus.Success -> {
                if (status.products.isEmpty()) {
                    barcodeResultEmpty()
                } else if (status.products.size == 1) {
                    selectIngredient(status.products.first())
                } else {
                    SelectIngredientBottomSheet(
                        sheetState = sheetState,
                        products = status.products,
                        onNameClick = {
                            selectIngredient(it)
                            scope.launch { sheetState.hide() }
                        },
                        onDirectInputClick = {
                            directInputClick()
                            scope.launch { sheetState.hide() }
                        },
                        onDismissed = { resetBarcodeScanStatus() }
                    )
                }
            }

            is BarcodeScanStatus.Error -> {
                barcodeResultError()
            }

            else -> Unit
        }
    }
}

@Composable
private fun CameraXScreen(
    modifier: Modifier = Modifier,
    barcodeScan: (String) -> Unit,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        AndroidView(
            factory = { context ->
                val previewView = PreviewView(context)
                val preview = Preview.Builder().build()
                val selector = CameraSelector.Builder()
                    .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                    .build()
                preview.surfaceProvider = previewView.surfaceProvider

                val imageAnalysis = ImageAnalysis.Builder()
                    .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                    .build()

                val options =
                    BarcodeScannerOptions.Builder().build()
                val scanner = BarcodeScanning.getClient(options)

                imageAnalysis.setAnalyzer(
                    Executors.newSingleThreadExecutor(),
                    BarcodeAnalyzer(scanner) { barcodes ->
                        barcodes.firstOrNull()?.rawValue?.let {
                            barcodeScan(it)
                        }
                    })

                try {
                    cameraProviderFuture.get().bindToLifecycle(
                        lifecycleOwner,
                        selector,
                        preview,
                        imageAnalysis
                    )
                } catch (e: Exception) {
                    Log.e("BarcodeScanner", "Camera binding failed", e)
                }

                previewView
            },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SelectIngredientBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    products: ImmutableList<Product>,
    onNameClick: (Product) -> Unit,
    onDirectInputClick: () -> Unit,
    onDismissed: () -> Unit
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = { onDismissed() },
        sheetState = sheetState,
        dragHandle = null
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                IconButton(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    onClick = { onDismissed() }
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = stringResource(R.string.bottom_sheet_close_button)
                    )
                }
            }

            LazyColumn {
                items(products) {
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = { onNameClick(it) }
                    ) { Text(it.name) }
                }

                item {
                    TextButton(
                        modifier = Modifier
                            .fillMaxWidth(),
                        onClick = { onDirectInputClick() }
                    ) { Text(stringResource(R.string.directInput)) }
                }
            }
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview
@Composable
private fun BarcodeScannerScreenPreview() {
    BarcodeScannerScreen(
        state = BarcodeState(),
        onIntent = {},
        snackbarHostState = SnackbarHostState()
    )
}
