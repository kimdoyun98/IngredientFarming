plugins {
    alias(libs.plugins.ingredientfarming.feature)
}

android {
    namespace = "com.project.permission"
}

dependencies {
    implementation(projects.core.model)
}
