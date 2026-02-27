package com.project.ui.util

import java.time.LocalDate
import java.time.temporal.ChronoUnit

fun daysLeft(targetDate: LocalDate): Long {
    val today = LocalDate.now() // 오늘 날짜
    return ChronoUnit.DAYS.between(today, targetDate).coerceAtLeast(0)
}
