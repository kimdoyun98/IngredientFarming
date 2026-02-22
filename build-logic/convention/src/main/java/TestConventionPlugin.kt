import com.project.convention.androidTestImplementation
import com.project.convention.debugImplementation
import com.project.convention.getLibrary
import com.project.convention.libs
import com.project.convention.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class TestConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            dependencies {
                testImplementation(libs.getLibrary("junit"))
                androidTestImplementation(libs.getLibrary("androidx-junit"))
                androidTestImplementation(libs.getLibrary("androidx-espresso-core"))
                androidTestImplementation(libs.getLibrary("androidx-ui-test-junit4"))
                debugImplementation(libs.getLibrary("androidx-ui-test-manifest"))
                androidTestImplementation(libs.getLibrary("androidx-compose-bom"))
            }
        }
    }
}
