import java.util.Properties

plugins {
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
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.kotlinx.serialization.json)

}
