package dev.meow.kawakaze.config

import dev.meow.kawakaze.KawakazeLib
import dev.meow.kawakaze.annotations.Config
import dev.meow.kawakaze.utils.reflection.getInstances
import io.github.classgraph.FieldInfo
import java.io.File
import kotlin.time.measureTime

@Config
object ConfigSystem {

    private val configs = mutableMapOf<Any, ConfigMetadata>()

    fun initialize() {
        measureTime {
            val instances = getInstances(Config::class.java)

            instances.forEach { instance ->
                KawakazeLib.LOGGER.info("Config: ${instance::class.simpleName}")
            }

        }.let {
            KawakazeLib.LOGGER.info("花费时间: $it")
        }

    }


    data class ConfigMetadata(
        val instance: Any,
        val name: String,
        val file: File,
        val properties: List<PropertyMetadata>
    )

    data class PropertyMetadata(
        val info: FieldInfo,
        val name: String
    )

}