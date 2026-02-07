package com.project.ingredient.barcode.barcode

import android.util.Log
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage

class BarcodeAnalyzer (
    private val barcodeScanner: BarcodeScanner,
    private val onBarcodesDetected: (List<Barcode>) -> Unit
) : ImageAnalysis.Analyzer {
    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        if (mediaImage != null) {
            val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)

            barcodeScanner.process(image)
                .addOnSuccessListener { barcodes ->
                    if (barcodes.isNotEmpty()) {
                        onBarcodesDetected(barcodes)
                    }
                }
                .addOnFailureListener {
                    Log.e("BarcodeAnalyzer", "Barcode scanning failed", it)
                }
                .addOnCompleteListener {
                    imageProxy.close()
                }
        }
    }
}
