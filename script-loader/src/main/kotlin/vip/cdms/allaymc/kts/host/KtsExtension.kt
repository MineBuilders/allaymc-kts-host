package vip.cdms.allaymc.kts.host

import org.allaymc.server.extension.Extension
import org.allaymc.server.plugin.AllayPluginManager

@Suppress("unused")
class KtsExtension : Extension() {
    override fun main(args: Array<String>?) {
        AllayPluginManager.registerLoaderFactory(KtsLoader.Factory())
    }
}
