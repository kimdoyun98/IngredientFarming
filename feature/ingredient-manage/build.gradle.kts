plugins {
    alias(libs.plugins.ingredientfarming.feature)
}

android {
    namespace = "com.project.ingredient_manage"
}

dependencies {
    implementation(projects.domain.ingredient)
}
