package dev.meow.kawakaze.annotations

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.CLASS)
annotation class Config(
    val name: String = ""
)

