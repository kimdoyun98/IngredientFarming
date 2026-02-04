plugins {
    alias(libs.plugins.ingredientfarming.android.library)
}

android {
    namespace = "com.project.data.ingredient"
}

dependencies {
    implementation(projects.core.network)
    implementation(projects.core.model)
    implementation(projects.domain.ingredient)

    implementation("com.squareup.retrofit2:retrofit:3.0.0")
}
