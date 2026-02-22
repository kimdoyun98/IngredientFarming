plugins {
    alias(libs.plugins.ingredientfarming.compose)
    alias(libs.plugins.ingredientfarming.test)
}

android {
    namespace = "com.project.designsystem"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }


}

dependencies {

}
