package com.project.ui.util

object RegexDate {
    private const val DATE_MASK = "####-##-##"
    private const val MASK = '#'
    private const val MAX_LENGTH = DATE_MASK.length
    private const val DATE_FORMAT = "\\D"

    fun regexDate(text: String): String {
        val cleanString = text.replace(DATE_FORMAT.toRegex(), "")
        val maskBuffer = StringBuilder()
        var maskIndex = 0
        var cleanIndex = 0

        while (maskIndex < MAX_LENGTH && cleanIndex < cleanString.length) {
            if (DATE_MASK[maskIndex] == MASK) {
                maskBuffer.append(cleanString[cleanIndex])
                cleanIndex++
            } else {
                maskBuffer.append(DATE_MASK[maskIndex])
            }
            maskIndex++
        }

        return maskBuffer.toString()
    }
}
