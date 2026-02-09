plugins {
    alias(libs.plugins.ingredientfarming.android.library)
    alias(libs.plugins.ingredientfarming.room)
    alias(libs.plugins.ingredientfarming.hilt)
}

android {
    namespace = "com.project.database"
}

dependencies {
    implementation(projects.core.model)
}
