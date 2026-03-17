package dev.meow.kawakaze.utils.reflection

import io.github.classgraph.ClassGraph
import io.github.classgraph.ClassInfo
import io.github.classgraph.ScanResult
import java.lang.reflect.Modifier

val scan: ScanResult by lazy {
    ClassGraph()
        .enableClassInfo()
        .enableAnnotationInfo()
        .rejectPackages(
            "net.minecraft",
            "com.mojang",
            "org.apache",
            "io.netty",
            "org.lwjgl",
            "com.google"
        )
        .scan()
}

fun getInstances(annotation: Class<out Annotation>, filter: (info: ClassInfo) -> Boolean = { true }): Map<Any, ClassInfo> {
    if (scan.isClosed) return emptyMap()

    val instances = scan.getClassesWithAnnotation(annotation)

    return instances.filter(filter)
        .mapNotNull { info ->
            val instance = createInstance<Any>(Class.forName(info.name))
            instance?.let { it to info }
        }.toMap()
}

val Class<*>.isObject: Boolean
    get() = declaredFields.any {
        it.name == "INSTANCE"
                && Modifier.isStatic(it.modifiers)
                && Modifier.isFinal(it.modifiers)
                && it.type == this
    }

val Class<*>.objectInstance: Any
    get() = declaredFields.first { it.name == "INSTANCE" }.apply { isAccessible = true }.get(null)


inline fun <reified T> createInstance(clazz: Class<*>): T? {
    return when {
        clazz.isInterface || clazz.isEnum || clazz.isAnnotation || clazz.isObject -> {
            clazz.objectInstance as? T
        }

        else -> {
            clazz.constructors
                .filterNot { Modifier.isAbstract(it.declaringClass.modifiers) }
                .firstOrNull { it.parameterCount == 0 }?.newInstance() as? T
        }
    }
}

class NoImplementException(msg: String) : IllegalStateException(msg)

