package cute.neko.kawakaze.config.setting.types

import cute.neko.kawakaze.config.setting.Setting

class BooleanSetting(
    name: String,
    default: Boolean,
) : Setting<Boolean>(name, default) {

    /**
     * Toggles current boolean value
     */
    fun toggle() {
        set(!get())
    }
}