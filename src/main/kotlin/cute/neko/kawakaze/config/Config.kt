package cute.neko.kawakaze.config

import com.google.gson.JsonElement
import cute.neko.kawakaze.config.ConfigSystem.gson
import cute.neko.kawakaze.config.setting.Setting
import cute.neko.kawakaze.config.setting.types.BooleanSetting
import cute.neko.kawakaze.config.setting.types.FloatSetting
import cute.neko.kawakaze.config.setting.types.ModeSettings
import java.io.FileWriter

/**
 * @author yuchenxue
 * @date 2025/09/03
 */

/**
 * @param name config name
 * @param id the mod-id
 */
open class Config(
    val name: String,
    id: String = ""
) {
    private val fold = if (id.isEmpty()) {
        ConfigSystem.CONFIG_FOLD
    } else {
        ConfigSystem.CONFIG_FOLD.resolve(id).apply {
            if (!exists()) {
                mkdir()
            }
        }
    }

    private val jsonFile = fold.resolve("$name.json")

    internal val settings = mutableListOf<Setting<*>>()

    fun load() {
        if (!jsonFile.exists()) {
            return
        }

        try {
            val config = gson.fromJson(jsonFile.readText(), JsonElement::class.java)
            deserializer(config)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun save() {
        try {
            val json = gson.toJson(this, Config::class.java)

            FileWriter(jsonFile).use { writer ->
                writer.write(json)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun deserializer(json: JsonElement) {
        val settings = json.asJsonObject.get("settings").asJsonArray
            .map { element -> element.asJsonObject }
            .associateBy { s -> s["name"].asString }

        this.settings.forEach { setting ->
            val settingJson = settings[setting.name] ?: return@forEach

            setting.deserialize(settingJson["value"])
        }
    }

    /**
     * Register a setting to the config
     */
    fun <T : Setting<*>> register(setting: T): T {
        settings.add(setting)
        return setting
    }

    protected fun setting(
        name: String, value: Boolean
    ) = register(BooleanSetting(name, value))

    protected fun setting(
        name: String, value: Float, min: Float, max: Float
    ) = register(FloatSetting(name, value, min, max))

    protected fun setting(
        name: String, modes: Array<String>, value: String
    ) = register(ModeSettings(name, modes, value))
}