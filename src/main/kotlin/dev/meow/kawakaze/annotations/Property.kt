package dev.meow.kawakaze.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FIELD)
annotation class Property(
    val name: String = ""
)
