package cute.neko.kawakaze.config.setting.types

import cute.neko.kawakaze.config.gson.strategy.Exclude
import cute.neko.kawakaze.config.setting.Setting

/**
 * @author yuchenxue
 * @date 2025/09/03
 */

class FloatSetting(
    name: String,
    default: Float,
    @Exclude private val min: Float,
    @Exclude private val max: Float
) : Setting<Float>(name, default) {

    override fun set(value: Float) {
        if (value !in min..max) {
            reset()
            return
        }

        super.set(value)
    }
}