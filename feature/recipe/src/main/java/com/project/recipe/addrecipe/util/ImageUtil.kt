package com.project.recipe.addrecipe.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.graphics.scale
import java.io.File

internal fun Uri.copyToInternalStorage(context: Context, recipeName: String): String {
    val fileName = "${recipeName}_image_${System.currentTimeMillis()}.jpg"
    val imageFile = File(context.filesDir, fileName)

    context.contentResolver.openInputStream(this)?.use { input ->
        imageFile.outputStream().use { output ->
            input.copyTo(output)
            output.flush()
        }
    }

    val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
    val resizedBitmap = bitmap.resizeBitmapKeepRatio(300, 300)

    imageFile.outputStream().use { outputStream ->
        resizedBitmap.compress(
            Bitmap.CompressFormat.JPEG,
            80,
            outputStream
        )

        outputStream.flush()
    }

    return imageFile.absolutePath
}

private fun Bitmap.resizeBitmapKeepRatio(
    maxWidth: Int,
    maxHeight: Int
): Bitmap {

    val width = this.width
    val height = this.height

    val ratioBitmap = width.toFloat() / height.toFloat()
    val ratioMax = maxWidth.toFloat() / maxHeight.toFloat()

    val finalWidth: Int
    val finalHeight: Int

    if (ratioMax > ratioBitmap) {
        finalHeight = maxHeight
        finalWidth = (maxHeight * ratioBitmap).toInt()
    } else {
        finalWidth = maxWidth
        finalHeight = (maxWidth / ratioBitmap).toInt()
    }

    return this.scale(finalWidth, finalHeight)
}
