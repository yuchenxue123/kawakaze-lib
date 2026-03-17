package dev.meow.kawakaze.config.serializer

object BooleanSerializer : Serializer<Boolean> {
    override fun serialize(src: Boolean): String {
        return src.toString()
    }

    override fun deserialize(text: String, origin: Boolean): Boolean {
        return when {
            text.equals("true", ignoreCase = true) -> true
            text.equals("false", ignoreCase = true) -> true
            else -> origin
        }
    }

}