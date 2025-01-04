package vip.cdms.allaymc.kts.host

import kotlinx.coroutines.runBlocking
import org.allaymc.api.plugin.PluginContainer
import org.allaymc.api.plugin.PluginDependency
import org.allaymc.api.plugin.PluginDescriptor
import org.allaymc.api.plugin.PluginLoader
import org.allaymc.server.plugin.DefaultPluginSource
import vip.cdms.allaymc.kts.definition.AllayScript
import vip.cdms.allaymc.kts.definition.ScriptInfo
import java.nio.file.Path
import kotlin.script.experimental.api.*
import kotlin.script.experimental.host.FileScriptSource
import kotlin.script.experimental.jvmhost.BasicJvmScriptingHost
import kotlin.script.experimental.jvmhost.createJvmCompilationConfigurationFromTemplate
import kotlin.script.experimental.util.filterByAnnotationType

class KtsLoader(path: Path) : PluginLoader {
    companion object {
        const val FileExtension = "allay.kts"
        val DefaultDataDirectory: Path = DefaultPluginSource.DEFAULT_PLUGIN_FOLDER.resolve("kotlin-scripting")
    }

    val host = BasicJvmScriptingHost()
    val source = FileScriptSource(path.toFile())
    val name = path.fileName.toString().run { substringBeforeLast(FileExtension) + substringAfterLast(FileExtension, "") }
    val plugin = KtsPlugin()

    lateinit var scriptInfo: ScriptInfo
    val compilationConfiguration = createJvmCompilationConfigurationFromTemplate<AllayScript> {
        refineConfiguration {
            onAnnotations(ScriptInfo::class) { context ->
                scriptInfo = context.collectedData?.get(ScriptCollectedData.collectedAnnotations)
                    ?.filterByAnnotationType<ScriptInfo>()?.firstOrNull()?.annotation ?: ScriptInfo()
                context.compilationConfiguration.asSuccess()
            }
        }
    }

    var compiled: CompiledScript
    init {
        runBlocking {
            val result = host.compiler(source, compilationConfiguration)
            if (result is ResultWithDiagnostics.Success) compiled = result.value
            else error("Failed to compile script: $result")  // TODO: do not crash the server
        }
    }

    override fun getPluginPath(): Path = source.file.toPath()
    private val pluginDescriptor by lazy {
        object : PluginDescriptor {
            override fun getName() = scriptInfo.name.ifEmpty { source.name }
            override fun getEntrance() = source.name
            override fun getDescription() = scriptInfo.description.ifEmpty { source.externalLocation.path }
            override fun getVersion() = scriptInfo.version.ifEmpty { "0.0.0" }
            override fun getAuthors() = scriptInfo.authors.toList()
            override fun getDependencies() = scriptInfo.dependencies.map { PluginDependency(it.name, it.version, it.optional) }
            override fun getWebsite() = scriptInfo.website
        }
    }
    override fun loadDescriptor() = pluginDescriptor
    override fun loadPlugin(): PluginContainer = PluginContainer.createPluginContainer(
        plugin,
        pluginDescriptor,
        this,
        DefaultDataDirectory.resolve(name)
    )

    class Factory : PluginLoader.Factory {
        override fun canLoad(path: Path) = path.toString().endsWith(FileExtension)
        override fun create(path: Path) = KtsLoader(path)
    }
}
