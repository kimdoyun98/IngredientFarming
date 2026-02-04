plugins {
    alias(libs.plugins.ingredientfarming.android.application)
}

android {
    namespace = "com.project.ingredientfarming"

    defaultConfig {
        applicationId = "com.project.ingredientfarming"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(projects.feature.ingredient)
    implementation(projects.data.ingredient)
}
