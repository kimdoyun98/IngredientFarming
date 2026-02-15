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
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

@Composable
internal fun BarcodeScannerScreen(
    modifier: Modifier = Modifier,
    state: () -> BarcodeState,
    onIntent: (BarcodeIntent) -> Unit,
) {

    BarcodeScannerScreen(
        modifier = modifier,
        scanStatus = state().scanStatus,
        barcodeScan = { onIntent(BarcodeIntent.BarcodeScan(it)) },
        selectIngredient = { onIntent(BarcodeIntent.SelectIngredient(it)) },
        directInputClick = { onIntent(BarcodeIntent.DirectInputClick) },
        resetBarcodeScanStatus = { onIntent(BarcodeIntent.SnackBarDismissed) },
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun BarcodeScannerScreen(
    modifier: Modifier,
    scanStatus: BarcodeScanStatus,
    barcodeScan: (String) -> Unit,
    selectIngredient: (Product) -> Unit,
    directInputClick: () -> Unit,
    resetBarcodeScanStatus: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val sheetState = rememberModalBottomSheetState()

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { innerPadding ->
        CameraXScreen(
            modifier = modifier.padding(innerPadding),
            barcodeScan = { barcodeScan(it) },
        )

        when (val status = scanStatus) {
            is BarcodeScanStatus.Scanning -> {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    IngredientFarmingCenterLoading()
                }
            }

            is BarcodeScanStatus.Success -> {
                if (status.products.isEmpty()) {
                    showSnackBar(
                        scope = scope,
                        snackBarHostState = snackBarHostState,
                        message = stringResource(R.string.no_search_product),
                        actionLabel = stringResource(R.string.directInput),
                        onActionPerformed = { directInputClick() },
                        onDismissed = { resetBarcodeScanStatus() }
                    )
                } else if (status.products.size == 1) {
                    selectIngredient(status.products.first())
                } else {
                    SelectIngredientBottomSheet(
                        modifier = modifier,
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
                showSnackBar(
                    scope = scope,
                    snackBarHostState = snackBarHostState,
                    message = stringResource(R.string.no_search_product),
                    actionLabel = stringResource(R.string.directInput),
                    onActionPerformed = { directInputClick() },
                    onDismissed = { resetBarcodeScanStatus() }
                )
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

    Box(modifier = modifier.fillMaxSize()) {
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

private fun showSnackBar(
    scope: CoroutineScope,
    snackBarHostState: SnackbarHostState,
    message: String,
    actionLabel: String,
    onActionPerformed: () -> Unit,
    onDismissed: () -> Unit
) {
    scope.launch {
        val result = snackBarHostState
            .showSnackbar(
                message = message,
                actionLabel = actionLabel,
                withDismissAction = true,
                duration = SnackbarDuration.Long
            )
        when (result) {
            SnackbarResult.ActionPerformed -> {
                onActionPerformed()
            }

            SnackbarResult.Dismissed -> {
                onDismissed()
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun SelectIngredientBottomSheet(
    modifier: Modifier = Modifier,
    sheetState: SheetState,
    products: List<Product>,
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
            Box(modifier = modifier.fillMaxWidth()) {
                IconButton(
                    modifier = modifier
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
                        modifier = modifier.fillMaxWidth(),
                        onClick = { onNameClick(it) }
                    ) { Text(it.name) }
                }

                item {
                    TextButton(
                        modifier = modifier.fillMaxWidth(),
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
        modifier = Modifier,
        scanStatus = BarcodeScanStatus.Success(
            products = listOf(
                Product(barcode = "1234567890123", name = "Sample Product 1"),
                Product(barcode = "1234567890124", name = "Sample Product 2")
            )
        ),
        barcodeScan = {},
        selectIngredient = {},
        directInputClick = {},
        resetBarcodeScanStatus = {}
    )
}
