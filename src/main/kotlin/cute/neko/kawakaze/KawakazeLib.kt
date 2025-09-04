package cute.neko.kawakaze

import cute.neko.kawakaze.config.ConfigRegistrar
import cute.neko.kawakaze.config.ConfigSystem
import net.fabricmc.api.ModInitializer

/**
 * @author yuchenxue
 * @date 2025/09/03
 */

object KawakazeLib : ModInitializer {

    override fun onInitialize() {
    }

    private val tasks = mutableListOf<(ConfigRegistrar) -> Unit>()

    /**
     * Add a task before load config
     */
    fun task(task: (registrar: ConfigRegistrar) -> Unit) {
        tasks.add(task)
    }

    fun start() {

        val register = ConfigRegistrar()

        tasks.forEach {
            it.invoke(register)
        }

        ConfigSystem.loadAll()
    }

    fun shutdown() {
        ConfigSystem.saveAll()
    }
}