package cute.neko.kawakaze

import cute.neko.kawakaze.service.ServiceManager
import net.fabricmc.api.ModInitializer

object KawakazeLib : ModInitializer {

    override fun onInitialize() {
    }

    fun initialize() {
        ServiceManager.initialize()
    }

    fun shutdown() {
        ServiceManager.shutdown()
    }
}