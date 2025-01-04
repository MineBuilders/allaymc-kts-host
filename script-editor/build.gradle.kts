plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.gradleup.shadow)
}

dependencies {
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    compileOnly(libs.allaymc.server)
    compileOnly(projects.scriptLoader)
}
