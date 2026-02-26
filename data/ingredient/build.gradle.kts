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
    implementation(projects.core.database)
    implementation(projects.core.common)

    implementation(libs.retrofit)
}
