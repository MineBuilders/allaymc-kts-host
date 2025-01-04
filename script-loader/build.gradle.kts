plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.gradleup.shadow)
}

dependencies {
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    compileOnly(libs.allaymc.server)

    // ktx core shared lib
    compileOnly(libs.kotlin.reflect)
    compileOnly(libs.kotlinx.coroutines.core)

    implementation(libs.kotlin.scripting.common)
    implementation(libs.kotlin.scripting.jvm)
    implementation(libs.kotlin.scripting.jvm.host)
    implementation(libs.kotlin.scripting.dependencies)
    implementation(libs.kotlin.scripting.dependencies.maven)
}
