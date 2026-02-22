import com.android.build.gradle.LibraryExtension
import com.project.convention.configureCompose
import com.project.convention.getLibrary
import com.project.convention.implementation
import com.project.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class ComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("ingredientfarming.android.library")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            extensions.configure<LibraryExtension> {
                configureCompose(this)
            }

            dependencies {
                implementation(libs.getLibrary("kotlinx-collections-immutable"))
            }
        }
    }
}
