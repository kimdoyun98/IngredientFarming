plugins {
    alias(libs.plugins.ingredientfarming.feature)
}

android {
    namespace = "com.project.home"
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(projects.domain.ingredient)
}
