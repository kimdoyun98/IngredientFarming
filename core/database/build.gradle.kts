plugins {
    alias(libs.plugins.ingredientfarming.android.library)
    alias(libs.plugins.ingredientfarming.room)
}

android {
    namespace = "com.project.database"
}

dependencies {
    implementation(projects.core.model)
}
