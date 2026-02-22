plugins {
    alias(libs.plugins.ingredientfarming.feature)
}

android {
    namespace = "com.project.home"
}

dependencies {
    implementation(projects.domain.ingredient)
}
