package dev.meow.kawakaze.config.serializer

interface Serializer<T : Any> {

    fun serialize(src: T): String

    fun deserialize(text: String, origin: T): T

}