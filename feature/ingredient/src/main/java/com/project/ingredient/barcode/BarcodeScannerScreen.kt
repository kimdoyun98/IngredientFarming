package com.project.ingredient.barcode

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.project.ingredient.barcode.contract.BarcodeIntent
import com.project.ingredient.barcode.contract.BarcodeScanStatus
import com.project.ingredient.barcode.contract.BarcodeState
import java.util.concurrent.Executors

@Composable
internal fun BarcodeScannerScreen(
    modifier: Modifier = Modifier,
    state: () -> BarcodeState,
    onIntent: (BarcodeIntent) -> Unit,
) {

    CameraXScreen(
        modifier = modifier,
        barcodeScan = { onIntent(BarcodeIntent.BarcodeScan(it)) },
    )

    when (val status = state().scanStatus) {

        is BarcodeScanStatus.Scanning -> {
            // Loading
        }

        is BarcodeScanStatus.Success -> {
            // Navigate
        }

        is BarcodeScanStatus.Error -> {
            // Toast
        }

        else -> Unit
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
