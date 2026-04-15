plugins {
    alias(libs.plugins.ingredientfarming.feature)
}

android {
    namespace = "com.project.ingredient"
}

dependencies {
    implementation(projects.domain.ingredient)
    implementation(libs.bundles.camera)
}
