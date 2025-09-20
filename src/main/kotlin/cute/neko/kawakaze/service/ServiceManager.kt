package cute.neko.kawakaze.service

import cute.neko.kawakaze.config.ConfigSystem
import cute.neko.kawakaze.service.types.HandlerService

object ServiceManager : Service {
    private val services = mutableListOf<Service>()

    init {
        register(
            ConfigSystem,
            HandlerService
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