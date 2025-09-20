package cute.neko.kawakaze.service

import cute.neko.kawakaze.config.ConfigSystem

object ServiceManager : Service {
    private val services = mutableListOf<Service>()

    init {
        register(
            ConfigSystem
        )
    }

    private fun register(vararg service: Service) {
        services += service
    }

    override fun initialize() {
        services.forEach(Service::initialize)
    }

    override fun shutdown() {
        services.forEach(Service::shutdown)
    }
}