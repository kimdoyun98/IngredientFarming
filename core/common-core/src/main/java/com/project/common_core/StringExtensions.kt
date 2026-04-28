package com.project.common_core

fun String.normalize() =
    this
        .trim()
        .lowercase()
        .replace(" ", "")
