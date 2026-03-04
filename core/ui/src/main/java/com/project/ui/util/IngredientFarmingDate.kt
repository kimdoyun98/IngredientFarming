package com.project.ui.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.project.ui.R
import java.time.LocalDate
import java.time.temporal.ChronoUnit

const val UNKNOWN_DATE = 10000L
fun daysLeft(targetDate: LocalDate): Long {
    val today = LocalDate.now()
    if (targetDate.year == 9999) return UNKNOWN_DATE

    return ChronoUnit.DAYS.between(today, targetDate).coerceAtLeast(0)
}

@Composable
fun getLeftDateText(leftDays: Long): String {
    return if (leftDays == UNKNOWN_DATE) {
        stringResource(R.string.ingredient_card_unknown_date)
    } else {
        stringResource(R.string.ingredient_card_left_date, leftDays)
    }
}

@Composable
fun getLocalDateText(date: LocalDate): String {
    return if (daysLeft(date) == UNKNOWN_DATE) {
        stringResource(R.string.ingredient_card_unknown_date)
    } else {
        date.toString()
    }
}
