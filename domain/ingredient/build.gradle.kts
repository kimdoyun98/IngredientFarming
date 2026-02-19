plugins {
    alias(libs.plugins.ingredientfarming.jvm)
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.javax.inject)

    implementation(libs.kotlinx.coroutines.core)

}
