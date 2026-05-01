plugins {
    alias(libs.plugins.ingredientfarming.android.library)
}

android {
    namespace = "com.project.data.recipe"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.database)
    implementation(projects.core.common)
    implementation(projects.domain.ingredient)

    implementation(libs.androidx.room.common.jvm)
    implementation(libs.androidx.paging.common)
}
