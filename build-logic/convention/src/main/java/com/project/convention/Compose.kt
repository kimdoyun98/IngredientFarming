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
            implementation(libs.getLibrary("androidx-activity-compose"))
            implementation(platform(libs.getLibrary("androidx-compose-bom")))
            implementation(libs.getLibrary("androidx-ui"))
            implementation(libs.getLibrary("androidx-ui-graphics"))
            implementation(libs.getLibrary("androidx-material3"))
            implementation(libs.getLibrary("androidx-ui-tooling-preview"))
            implementation(libs.getLibrary("androidx-navigation-compose"))
            implementation(libs.getLibrary("androidx-hilt-navigation-compose"))
            implementation(libs.getLibrary("androidx-material-icons-extended"))
            debugImplementation(libs.getLibrary("androidx-ui-tooling"))
        }
    }
}
