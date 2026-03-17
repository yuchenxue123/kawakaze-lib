package dev.meow.kawakaze.config

import dev.meow.kawakaze.annotations.Config
import dev.meow.kawakaze.annotations.Property
import dev.meow.kawakaze.config.serializer.BooleanSerializer
import dev.meow.kawakaze.config.serializer.IntegerSerializer
import dev.meow.kawakaze.config.serializer.SerializerDispatcher
import dev.meow.kawakaze.config.serializer.UnregisterSerializerException
import dev.meow.kawakaze.utils.reflection.getInstances
import net.minecraft.Minecraft
import java.io.File
import java.lang.reflect.Field

@Config("self")
object ConfigSystem {

    internal val CONFIG_FOLD = File(
        Minecraft.getMinecraft().mcDataDir, "configs"
    ).apply {
        if (!exists()) {
            mkdirs()
        }
    }

    val dispatcher = SerializerDispatcher().apply {
        register(BooleanSerializer)
        register(IntegerSerializer)
    }

    @Property
    val range = 5

    @Property
    val fly = true

    private val configs = mutableMapOf<Any, ConfigMetadata>()

    fun initialize() {
        val instances = getInstances(Config::class.java)

        instances.forEach { (instance, classInfo) ->
            val annotation = classInfo.getAnnotationInfo(Config::class.java)

            val name = annotation.let {
                val param = annotation.parameterValues.getValue("name") as? String ?: ""

                if (param.isBlank() || param.isEmpty()) {
                    return@let classInfo.simpleName
                }

                return@let param
            }

            val file = CONFIG_FOLD.resolve("$name.cfg")

            val properties = instance::class.java.declaredFields
                .filter { it.isAnnotationPresent(Property::class.java) }
                .mapNotNull { field ->
                    field.isAccessible = true
                    val annotation = field.getAnnotation(Property::class.java)

                    val name = annotation.let {
                        val param = it.name

                        if (param.isBlank() || param.isEmpty()) {
                            return@let field.name
                        }

                        return@let param
                    }

                    PropertyMetadata(
                        field = field,
                        name = name
                    )
                }

            val configMetadata = ConfigMetadata(
                instance = instance,
                name = name,
                file = file,
                properties = properties
            )

            configs.putIfAbsent(instance, configMetadata)
        }
    }

    fun saveAll() {
        configs.forEach { (instance, config) ->
            config.file.printWriter().use { writer ->
                config.properties.forEach { (field, name) ->
                    try {
                        val line = dispatcher.serialize(name, field.get(instance))
                        writer.println(line)
                    } catch (e: UnregisterSerializerException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    fun loadAll() {
        configs.forEach { (instance, config) ->
            config.file.readLines()
                .filter { it.isNotBlank() && !it.startsWith('#') }
                .forEach { line ->
                    try {

                    } catch (e: UnregisterSerializerException) {
                        e.printStackTrace()
                    }
                }
        }
    }


    data class ConfigMetadata(
        val instance: Any,
        val name: String,
        val file: File,
        val properties: List<PropertyMetadata>
    )

    data class PropertyMetadata(
        val field: Field,
        val name: String
    )

}