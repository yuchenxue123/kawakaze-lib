package dev.meow.kawakaze.config.serializer

object IntegerSerializer : Serializer<Int> {
    override fun serialize(src: Int): String {
        return src.toString()
    }

    override fun deserialize(text: String, origin: Int): Int {
        return try {
            text.toInt()
        } catch (_: NumberFormatException) {
            origin
        }
    }

}