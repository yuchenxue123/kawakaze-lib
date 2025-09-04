package cute.neko.kawakaze.config

/**
 * @author yuchenxue
 * @date 2025/09/04
 */

class ConfigRegistrar internal constructor() {
    fun register(config: Config) {
        ConfigSystem.register(config)
    }
}