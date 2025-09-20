package cute.neko.kawakaze

import cute.neko.event.EventListener
import cute.neko.event.handler
import cute.neko.kawakaze.events.MinecraftInitializeEvent
import cute.neko.kawakaze.events.MinecraftShutdownEvent
import cute.neko.kawakaze.service.ServiceManager
import net.fabricmc.api.ModInitializer

object KawakazeLib : ModInitializer, EventListener {

    override fun onInitialize() {
        // early initialize before minecraft
        EventHandler.initialize()
    }

    @Suppress("unused")
    private val initializeHandler = handler<MinecraftInitializeEvent> {
        // services initialize
        ServiceManager.initialize()
    }

    @Suppress("unused")
    private val shutdownHandler = handler<MinecraftShutdownEvent> {
        // services shutdown
        ServiceManager.shutdown()

        // unregister event subscriber
        EventHandler.shutdown()
    }
}