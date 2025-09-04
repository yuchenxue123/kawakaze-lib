package cute.neko.kawakaze

import cute.neko.kawakaze.config.ConfigRegistrar
import cute.neko.kawakaze.config.ConfigSystem
import cute.neko.kawakaze.config.ConfigTask
import net.fabricmc.api.ModInitializer

/**
 * @author yuchenxue
 * @date 2025/09/03
 */

object KawakazeLib : ModInitializer {

    override fun onInitialize() {
    }

    fun start() {
        ConfigTask.runBeforeTasks(ConfigRegistrar())

        ConfigSystem.loadAll()

        ConfigTask.runAfterTasks()
    }

    fun shutdown() {
        ConfigSystem.saveAll()
    }
}