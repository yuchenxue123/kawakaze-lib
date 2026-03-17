package dev.meow.kawakaze.config.serializer

import kotlin.reflect.KClass

class SerializerDispatcher {

    val serializers = mutableMapOf<KClass<*>, Serializer<*>>()

    inline fun <reified T : Any> register(serializer: Serializer<T>) {
        serializers[T::class] = serializer
    }

    fun serialize(key: String, value: Any): String {
        val clazz = value::class

        @Suppress("UNCHECKED_CAST")
        val serializer = serializers[clazz] as? Serializer<Any>
            ?: throw UnregisterSerializerException("No serializer registered for ${clazz.qualifiedName}")

        val value = serializer.serialize(value)

        return "$key=$value"
    }

    // This is wrong.
    inline fun <reified T : Any> deserialize(line: String, origin: T): Pair<String, T> {
        val parts = line.split("=", limit = 2)

        val key = parts[0]
        val value = parts[1]

        @Suppress("UNCHECKED_CAST")
        val serializer = serializers[T::class] as? Serializer<T>
            ?: throw UnregisterSerializerException("No serializer registered for ${T::class.qualifiedName}")

        return key to serializer.deserialize(value, origin)
    }

}