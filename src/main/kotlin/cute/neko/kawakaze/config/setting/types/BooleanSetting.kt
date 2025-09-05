package cute.neko.kawakaze.config.setting.types

import cute.neko.kawakaze.config.setting.Setting

class BooleanSetting @JvmOverloads constructor(
    name: String,
    default: Boolean = false,
) : Setting<Boolean>(name, default) {

    /**
     * Toggles current boolean value
     */
    fun toggle() {
        set(!get())
    }
}