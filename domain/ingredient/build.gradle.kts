plugins {
    alias(libs.plugins.ingredientfarming.jvm)
}

dependencies {
    implementation(projects.core.model)
    implementation(libs.javax.inject)

    implementation(libs.kotlinx.coroutines.core)
    implementation("androidx.paging:paging-common:3.4.2")
}
