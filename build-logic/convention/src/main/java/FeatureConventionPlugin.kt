import com.project.convention.getLibrary
import com.project.convention.implementation
import com.project.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class FeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("ingredientfarming.android.library")
                apply("ingredientfarming.compose")
                apply("ingredientfarming.test")
            }

            dependencies {
                implementation(project(":core:designsystem"))
                implementation(project(":core:navigation"))
                implementation(project(":core:ui"))

                implementation(libs.getLibrary("orbit-compose"))
                implementation(libs.getLibrary("orbit-viewmodel"))
            }
        }
    }
}
