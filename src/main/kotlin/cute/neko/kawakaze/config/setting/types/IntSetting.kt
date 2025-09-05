package cute.neko.kawakaze.config.setting.types

import cute.neko.kawakaze.config.gson.strategy.Exclude
import cute.neko.kawakaze.config.setting.Setting

class IntSetting @JvmOverloads constructor(
    name: String,
    default: Int,
    @Exclude private val min: Int = 0,
    @Exclude private val max: Int = 20
) : Setting<Int>(name, default) {

    /**
     * Overwrites the [cute.neko.kawakaze.config.setting.Setting.set] method
     * to add boundary validation for int value.
     *
     * If the provided [value] is outbound the range of [min]..[max],
     * this method will call [reset] instead of set the [value].
     *
     * @param value new int value to set.
     */
    override fun set(value: Int) {
        if (value !in min..max) {
            reset()
            return
        }

        super.set(value)
    }
}