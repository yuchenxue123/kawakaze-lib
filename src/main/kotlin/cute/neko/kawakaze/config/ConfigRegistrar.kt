package cute.neko.kawakaze.config

class ConfigRegistrar internal constructor() {
    fun register(config: Config) {
        ConfigSystem.register(config)
    }
}