package cute.neko.kawakaze.config.gson.serializer

import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import cute.neko.kawakaze.config.Config
import java.lang.reflect.Type

/**
 * @author yuchenxue
 * @date 2025/09/04
 */

object ConfigSerializer : JsonSerializer<Config> {
    override fun serialize(
        src: Config, typeOfSrc: Type, context: JsonSerializationContext
    ) = JsonObject().apply {
        add("settings", context.serialize(src.settings))
    }
}