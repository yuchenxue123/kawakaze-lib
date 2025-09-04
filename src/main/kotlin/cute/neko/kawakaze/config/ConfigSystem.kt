package cute.neko.kawakaze.config

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import cute.neko.kawakaze.config.gson.serializer.ConfigSerializer
import cute.neko.kawakaze.config.gson.strategy.ExcludeStrategy
import net.minecraft.Minecraft
import java.io.File

object ConfigSystem {

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
        .create()

    internal fun register(config: Config) {
        configs.add(config)
    }

    internal fun loadAll() {
        configs.forEach(Config::load)
    }

    internal fun saveAll() {
        configs.forEach(Config::save)
    }
}