plugins {
    alias(libs.plugins.ingredientfarming.feature)
}

android {
    namespace = "com.project.recipe"
}

dependencies {
    implementation(libs.coil.compose)
    implementation(projects.domain.ingredient)
    implementation(projects.core.commonCore)
    implementation(libs.androidx.paging.compose)
}
