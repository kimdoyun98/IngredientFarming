package com.project.convention

import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.implementation(provider: Provider<*>) {
    "implementation"(provider)
}

fun DependencyHandlerScope.implementation(project: Project) {
    "implementation"(project)
}

fun DependencyHandlerScope.ksp(provider: Provider<*>) {
    "ksp"(provider)
}

fun DependencyHandlerScope.androidTestImplementation(provider: Provider<*>) {
    "androidTestImplementation"(provider)
}
