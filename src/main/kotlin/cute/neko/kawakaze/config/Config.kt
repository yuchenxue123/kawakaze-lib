package cute.neko.kawakaze.config

import com.google.gson.JsonElement
import cute.neko.kawakaze.config.ConfigSystem.gson
import cute.neko.kawakaze.config.setting.Setting
import cute.neko.kawakaze.config.setting.types.BooleanSetting
import cute.neko.kawakaze.config.setting.types.FloatSetting
import cute.neko.kawakaze.config.setting.types.IntSetting
import cute.neko.kawakaze.config.setting.types.ModeSetting
import java.io.FileWriter

/**
 * A config for a mod,
 * you should use [cute.neko.kawakaze.KawakazeLib.task] to add a register task.
 *
 * @param name The name the config.
 * @param id The mod id.
 */

open class Config @JvmOverloads constructor(
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

    internal fun load() {
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

    internal fun save() {
        try {
            val json = gson.toJson(this, Config::class.java)

            FileWriter(jsonFile).use { writer ->
                writer.write(json)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    internal fun deserializer(json: JsonElement) {
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

    @JvmOverloads
    protected fun setting(
        name: String, value: Boolean = false
    ) = register(BooleanSetting(name, value))

    @JvmOverloads
    protected fun setting(
        name: String, value: Float, min: Float = 0f, max: Float = 10f
    ) = register(FloatSetting(name, value, min, max))

    @JvmOverloads
    protected fun setting(
        name: String, modes: Array<String>, value: String = modes[0]
    ) = register(ModeSetting(name, modes, value))

    @JvmOverloads
    protected fun setting(
        name: String, value: Int, min: Int = 0, max: Int = 20
    ) = register(IntSetting(name, value, min, max))
}