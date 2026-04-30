plugins {
    alias(libs.plugins.ingredientfarming.android.library)
    alias(libs.plugins.ingredientfarming.room)
}

android {
    namespace = "com.project.database"
}

dependencies {
    implementation(projects.core.model)
    implementation("androidx.paging:paging-runtime:3.4.2")
    implementation("androidx.room:room-paging:2.8.4")
}
