package com.project.convention

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

@Suppress("UnstableApiUsage")
internal fun Project.configureCompose(commonExtension: BaseExtension) {
    commonExtension.apply {
        buildFeatures.apply {
            compose = true
        }

        dependencies {
            implementation(libs.getBundle("compose"))
            implementation(libs.getBundle("navigation"))
            implementation(platform(libs.getLibrary("androidx-compose-bom")))
            debugImplementation(libs.getLibrary("androidx-ui-tooling"))
        }
    }
}
