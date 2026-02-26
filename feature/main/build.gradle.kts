plugins {
    alias(libs.plugins.ingredientfarming.feature)
}

android {
    namespace = "com.project.main"
}

dependencies {
    implementation(projects.feature.home)
    implementation(projects.feature.ingredientAdd)
    implementation(projects.feature.ingredientManage)
}
