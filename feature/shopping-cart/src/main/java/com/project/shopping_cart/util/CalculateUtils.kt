package com.project.shopping_cart.util

internal fun getPercentage(
    current: Int,
    total: Int,
): Int = try {
    (current * 100) / total
} catch (e: ArithmeticException) {
    0
}
