package cute.neko.kawakaze.config.setting

import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import cute.neko.kawakaze.config.ConfigSystem.gson
import cute.neko.kawakaze.config.gson.strategy.Exclude
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * @author yuchenxue
 * @date 2025/09/03
 */

abstract class Setting<T>(
    val name: String,
    @Exclude val default: T
) : ReadWriteProperty<Any?, T> {

    protected var value: T = default

    open fun set(value: T) {
        this.value = value
    }

    fun get(): T = value

    fun reset() = set(default)

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        this.value = value
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

    @Suppress("UNCHECK_CAST")
    internal fun deserialize(element: JsonElement) {
        val clazz = value?.javaClass ?: return

        var v: T? = null

        runCatching {
            v = gson.fromJson(element, clazz) as T
        }.onSuccess {
            set(v ?: default)
        }.onFailure {
            error("Failed to deserialize value")
        }
    }
}