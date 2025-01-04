plugins {
    alias(libs.plugins.kotlin.jvm)
}

dependencies {
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    compileOnly(libs.allaymc.server)
    compileOnly(projects.scriptLoader)
}
