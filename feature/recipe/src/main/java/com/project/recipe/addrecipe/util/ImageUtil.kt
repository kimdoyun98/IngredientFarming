package com.project.recipe.addrecipe.util

import android.content.Context
import android.net.Uri
import java.io.File

internal fun Uri.copyToInternalStorage(context: Context): String {
    val fileName = "recipe_image_${System.currentTimeMillis()}.jpg"
    val file = File(context.filesDir, fileName)

    context.contentResolver.openInputStream(this)?.use { input ->
        file.outputStream().use { output ->
            input.copyTo(output)
        }
    }

    return file.absolutePath
}
