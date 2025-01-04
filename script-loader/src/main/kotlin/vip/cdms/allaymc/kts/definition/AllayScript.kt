package vip.cdms.allaymc.kts.definition

import org.allaymc.api.server.Server
import org.allaymc.server.Allay
import vip.cdms.allaymc.kts.host.KtsLoader
import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.ScriptCompilationConfiguration
import kotlin.script.experimental.api.defaultImports
import kotlin.script.experimental.jvm.dependenciesFromClassloader
import kotlin.script.experimental.jvm.jvm

@KotlinScript(
    fileExtension = KtsLoader.FileExtension,
    compilationConfiguration = AllayScriptCompilationConfiguration::class,
)
abstract class AllayScript

object AllayScriptCompilationConfiguration : ScriptCompilationConfiguration({
    defaultImports(
        ScriptInfo::class,
        Server::class,
    )
    jvm {
        dependenciesFromClassloader(classLoader = Allay::class.java.classLoader, wholeClasspath = true)
        dependenciesFromClassloader(classLoader = Allay.EXTRA_RESOURCE_CLASS_LOADER, wholeClasspath = true)
        dependenciesFromClassloader(classLoader = KtsLoader::class.java.classLoader, wholeClasspath = true)
    }
}) {
    private fun readResolve(): Any = AllayScriptCompilationConfiguration
}
