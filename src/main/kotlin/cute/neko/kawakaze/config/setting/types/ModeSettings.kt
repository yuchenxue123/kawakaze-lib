package cute.neko.kawakaze.config.setting.types

import cute.neko.kawakaze.config.setting.Setting

/**
 * @author yuchenxue
 * @date 2025/09/04
 */

class ModeSettings(
    name: String,
    private val modes: Array<String>,
    default: String = modes[0]
) : Setting<String>(name, default) {

    override fun set(value: String) {
        val newMode = modes.find { it == value } ?: default

        super.set(newMode)
    }
}