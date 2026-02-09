import com.project.convention.getLibrary
import com.project.convention.implementation
import com.project.convention.ksp
import com.project.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class RoomPlugin: Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }

            dependencies {
                implementation(libs.getLibrary("androidx.room.ktx"))
                implementation(libs.getLibrary("androidx.room.runtime"))
                ksp(libs.getLibrary("androidx.room.compiler"))
            }
        }
    }
}
