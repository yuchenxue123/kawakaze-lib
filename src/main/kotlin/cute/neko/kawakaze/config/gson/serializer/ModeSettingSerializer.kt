package cute.neko.kawakaze.config.gson.serializer

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import cute.neko.kawakaze.config.setting.types.ModeSetting
import java.lang.reflect.Type

object ModeSettingSerializer : JsonSerializer<ModeSetting> {
    override fun serialize(
        src: ModeSetting,
        typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement = JsonObject().apply {
        addProperty("name", src.name)
        add("modes", context.serialize(src.modes))
        add("value", context.serialize(src.default))
    }
}