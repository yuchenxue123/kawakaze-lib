package cute.neko.kawakaze.config.setting.types

import cute.neko.kawakaze.config.setting.Setting

/**
 * @author yuchenxue
 * @date 2025/09/03
 */

class BooleanSetting(
    name: String,
    default: Boolean,
) : Setting<Boolean>(name, default) {

    fun toggle() {
        set(!get())
    }
}