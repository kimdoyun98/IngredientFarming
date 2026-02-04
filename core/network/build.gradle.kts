import java.util.Properties

plugins {
//    id("java-library")
//    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.ingredientfarming.android.library)
    alias(libs.plugins.kotlin.serialization)
}

val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(localPropertiesFile.inputStream())
}

android {
    namespace = "com.project.network"

    defaultConfig {
        buildConfigField(
            "String",
            "BARCODE_API_KEY",
            "\"${localProperties["BARCODE_API_KEY"]}\""
        )
    }

    buildFeatures { buildConfig = true }
}

dependencies {
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.okhttp3:logging-interceptor:5.3.2")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.1")

}
