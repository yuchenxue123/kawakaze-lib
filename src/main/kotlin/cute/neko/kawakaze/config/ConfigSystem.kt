package cute.neko.kawakaze.config

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import cute.neko.kawakaze.config.gson.serializer.ConfigSerializer
import cute.neko.kawakaze.config.gson.serializer.ModeSettingSerializer
import cute.neko.kawakaze.config.gson.strategy.ExcludeStrategy
import cute.neko.kawakaze.config.setting.types.ModeSetting
import cute.neko.kawakaze.prepare.Prepares.CONFIG_PREPARES
import cute.neko.kawakaze.prepare.types.ConfigPreparable
import cute.neko.kawakaze.service.Service
import net.minecraft.Minecraft
import java.io.File

object ConfigSystem : Service {

    internal val CONFIG_FOLD = File(
        Minecraft.getMinecraft().mcDataDir, "configs"
    ).apply {
        if (!exists()) {
            mkdirs()
        }
    }

    private val configs = mutableListOf<Config>()

    val gson: Gson = GsonBuilder()
        .setPrettyPrinting()
        .addSerializationExclusionStrategy(ExcludeStrategy)
        .registerTypeAdapter(Config::class.java, ConfigSerializer)
        .registerTypeAdapter(ModeSetting::class.java, ModeSettingSerializer)
        .create()

    internal fun register(config: Config) {
        configs.add(config)
    }

    override fun initialize() {
        CONFIG_PREPARES.forEach(ConfigPreparable::prepare)
        configs.forEach(Config::load)
    }

    override fun shutdown() {
        configs.forEach(Config::save)
    }
}