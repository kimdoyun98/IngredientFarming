import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.tools.build.gradle.plugin)
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        plugins {
            register("AndroidApplication") {
                id = "ingredientfarming.android.application"
                implementationClass = "AndroidApplicationConventionPlugin"
            }

            register("AndroidLibrary") {
                id = "ingredientfarming.android.library"
                implementationClass = "AndroidLibraryConventionPlugin"
            }

            register("Hilt") {
                id = "ingredientfarming.hilt"
                implementationClass = "HiltPlugin"
            }

            register("Compose") {
                id = "ingredientfarming.compose"
                implementationClass = "ComposeConventionPlugin"
            }

            register("Feature") {
                id = "ingredientfarming.feature"
                implementationClass = "FeatureConventionPlugin"
            }

            register("Jvm") {
                id = "ingredientfarming.jvm"
                implementationClass = "JvmLibraryConventionPlugin"
            }

            register("Room") {
                id = "ingredientfarming.room"
                implementationClass = "RoomPlugin"
            }
        }
    }
}
