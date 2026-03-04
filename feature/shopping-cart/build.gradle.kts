plugins {
    alias(libs.plugins.ingredientfarming.feature)
}

android {
    namespace = "com.project.shopping_cart"
}

dependencies {
    implementation(projects.domain.shoppingCart)
    implementation(projects.domain.ingredient)
}
