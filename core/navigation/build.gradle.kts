plugins {
    alias(libs.plugins.ingredientfarming.compose)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.project.navigation"
}

dependencies {
    implementation(libs.androidx.navigation.compose)
}
