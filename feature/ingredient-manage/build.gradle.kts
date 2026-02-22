plugins {
    alias(libs.plugins.ingredientfarming.feature)
}

android {
    namespace = "com.project.ingredient_manage"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(projects.core.model)

    implementation(projects.domain.ingredient)
}
