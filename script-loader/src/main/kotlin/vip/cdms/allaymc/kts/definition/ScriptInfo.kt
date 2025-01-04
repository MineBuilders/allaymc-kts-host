package vip.cdms.allaymc.kts.definition

@Target(AnnotationTarget.FILE)
@Retention(AnnotationRetention.SOURCE)
annotation class ScriptInfo(
    val name: String = "",
    val description: String = "",
    val version: String = "",
    val authors: Array<String> = [ "Anonymous" ],
    val website: String = "",
    val dependencies: Array<Dependency> = [],
) {
    annotation class Dependency(
        val name: String,
        val version: String = "",
        val optional: Boolean = true,
    )
}
