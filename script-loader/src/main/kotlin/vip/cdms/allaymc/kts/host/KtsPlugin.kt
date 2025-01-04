package vip.cdms.allaymc.kts.host

import org.allaymc.api.plugin.Plugin
import vip.cdms.allaymc.kts.definition.AllayScript
import kotlin.script.experimental.jvmhost.createJvmEvaluationConfigurationFromTemplate

class KtsPlugin : Plugin() {
    val loader by lazy { pluginContainer.loader as KtsLoader }

    val evaluationConfiguration = createJvmEvaluationConfigurationFromTemplate<AllayScript> {}

    override fun onLoad() = loader.host.runInCoroutineContext {
        loader.host.evaluator(loader.compiled, evaluationConfiguration)
        Unit
    }

    // TODO: scripts shared host, import other script...
}
