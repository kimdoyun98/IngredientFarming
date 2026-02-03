import org.gradle.api.Plugin
import org.gradle.api.Project

class FeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("ingredientfarming.android.library")
                apply("ingredientfarming.hilt")
                apply("ingredientfarming.compose")
            }
        }
    }
}
