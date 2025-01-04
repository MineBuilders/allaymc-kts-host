### `hello.allay.kts`

```kt
@file:ScriptInfo(
    name = "Hello Allay",
    description = "Test kotlin script for Allay.",
    authors = ["Cdm2883"],
)

import org.allaymc.api.eventbus.event.player.PlayerJoinEvent

println("Hello world from Kotlin Scripting!")

Server.getInstance().eventBus.registerListenerFor(PlayerJoinEvent::class.java) { event ->
    println("Hello my friend ${event.player.displayName}!")
}
```
