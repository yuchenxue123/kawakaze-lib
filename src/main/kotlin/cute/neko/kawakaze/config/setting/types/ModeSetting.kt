package cute.neko.kawakaze.config.setting.types

import cute.neko.kawakaze.config.setting.Setting

class ModeSetting(
    name: String,
    internal val modes: Array<String>,
    default: String = modes[0]
) : Setting<String>(name, default) {



    /**
     * Overrides the [Setting.set] method
     * to ensure the assigned mode is valid.
     *
     * If the provided [value] does not exist in [modes],
     * it will set to [default] value.
     *
     * @param value The new mode to set.
     */
    override fun set(value: String) {
        val newMode = modes.find { it == value } ?: default

        super.set(newMode)
    }
}