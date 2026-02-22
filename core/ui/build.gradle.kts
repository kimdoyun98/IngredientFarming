plugins {
    alias(libs.plugins.ingredientfarming.compose)
    alias(libs.plugins.ingredientfarming.test)
}

android {
    namespace = "com.project.ui"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.model)
}
