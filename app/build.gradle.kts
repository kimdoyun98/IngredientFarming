plugins {
    alias(libs.plugins.ingredientfarming.android.application)
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
}
