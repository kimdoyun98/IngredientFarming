plugins {
    alias(libs.plugins.ingredientfarming.feature)
}

android {
    namespace = "com.project.ingredient"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.domain.ingredient)

    implementation(libs.barcode.scanning)

    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.view)
}
