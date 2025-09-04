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

    /**
     * Overwrites the [cute.neko.kawakaze.config.setting.Setting.set] method
     * to add boundary validation for float value.
     *
     * If the provided [value] is outbound the range of [min]..[max],
     * this method will call [reset] instead of set the [value].
     *
     * @param value new float value to set.
     */
    override fun set(value: Float) {
        if (value !in min..max) {
            reset()
            return
        }

        super.set(value)
    }
}