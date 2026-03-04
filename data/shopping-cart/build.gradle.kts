plugins {
    alias(libs.plugins.ingredientfarming.android.library)
}

android {
    namespace = "com.project.data.shoppinng_cart"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.domain.shoppingCart)
    implementation(projects.core.database)
    implementation(projects.core.common)

    implementation(libs.androidx.room.common.jvm)
}
