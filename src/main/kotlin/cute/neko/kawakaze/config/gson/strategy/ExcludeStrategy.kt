package cute.neko.kawakaze.config.gson.strategy

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes

/**
 * @author yuchenxue
 * @date 2025/09/04
 */

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Exclude()

object ExcludeStrategy : ExclusionStrategy {
    override fun shouldSkipClass(clazz: Class<*>?) = false
    override fun shouldSkipField(field: FieldAttributes) = field.getAnnotation(Exclude::class.java) != null
}
