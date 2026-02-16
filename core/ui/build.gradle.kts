plugins {
    alias(libs.plugins.ingredientfarming.compose)
}

android {
    namespace = "com.project.ui"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.core.model)
    debugImplementation(libs.androidx.ui.tooling)
}
