plugins {
    alias(libs.plugins.ingredientfarming.android.application)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.project.ingredientfarming"

    defaultConfig {
        applicationId = "com.project.ingredientfarming"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(projects.feature.main)
    implementation(projects.data.ingredient)
    implementation(projects.data.shoppingCart)
    implementation(projects.core.common)

    implementation(projects.domain.ingredient)
    implementation(projects.core.model)

    implementation(libs.kotlinx.serialization.json)
}
