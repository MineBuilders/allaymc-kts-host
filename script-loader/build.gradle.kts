plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.gradleup.shadow)
}

dependencies {
    @Suppress("VulnerableLibrariesLocal", "RedundantSuppression")
    compileOnly(libs.allaymc.server)

    // ktx core shared lib
    val sharedLibs = arrayOf(
        libs.kotlin.stdlib.asProvider().get(),
        libs.kotlin.stdlib.jdk7.get(),
        libs.kotlin.stdlib.jdk8.get(),
        libs.kotlin.reflect.get(),
        libs.kotlinx.coroutines.core.get(),
    )
    sharedLibs.forEach { compileOnly(it) }
    val excludeSharedLibs = Action<ExternalModuleDependency> {
        sharedLibs.forEach { exclude(group = it.group, module = it.name) }
    }

    implementation(libs.kotlin.scripting.common, excludeSharedLibs)
    implementation(libs.kotlin.scripting.jvm, excludeSharedLibs)
    implementation(libs.kotlin.scripting.jvm.host, excludeSharedLibs)
    implementation(libs.kotlin.scripting.dependencies, excludeSharedLibs)
    implementation(libs.kotlin.scripting.dependencies.maven, excludeSharedLibs)
}
