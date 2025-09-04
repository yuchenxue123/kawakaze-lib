import org.gradle.kotlin.dsl.accessors.runtime.addDependencyTo

plugins {
    kotlin("jvm")
    id("fml-loom")
}

// Info
val mod_name: String by project
val mod_id: String by project
val mod_version: String by project
val mod_description: String by project
val access_widener: String by project
val icon_path: String by project
// Loader
val loader_version: String by project
val minecraft_version: String by project
// Dependencies
val kotlin_version: String by project

version = mod_version

base {
    archivesName = mod_name
}

repositories {
    maven {
        name = "Jitpack"
        url = uri("https://jitpack.io")
    }
}

loom {
    accessWidenerPath = file("src/main/resources/$access_widener")
    mergedMinecraftJar()
    fml = File("libs/loader-$loader_version.jar")

    mods {
        create(mod_name) {
            sourceSet(sourceSets.main.get())
        }
    }
}

val packageImplementation = configurations.register("packageImplementation") {
    configurations.implementation.get().extendsFrom(this)
}

fun DependencyHandler.packageImplementation(dependencyNotation: Any) {
    add("packageImplementation", dependencyNotation)
}

fun DependencyHandler.packageImplementation(dependencyNotation: Any, dependencyConfiguration: Action<ExternalModuleDependency>) {
    addDependencyTo(this,"packageImplementation", dependencyNotation, dependencyConfiguration)
}

dependencies {
    minecraft("com.mojang:minecraft:$minecraft_version")
    mappings(loom.fmlMCPMappings())
    implementation(files(loom.fml.toPath()))

    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version")
}

val properties = mapOf(
    "id" to mod_id,
    "name" to mod_name,
    "version" to mod_version,
    "description" to mod_description,
    "widener" to access_widener,
    "icon" to icon_path
)

tasks.processResources {
    inputs.property("version", mod_version)

    filesMatching("fml.mod.json") {
        expand(properties)
    }
}

tasks.jar {
    from({
        packageImplementation.get().map {
            if (it.isDirectory) it else zipTree(it)
        }
    }) {
        exclude("META-INF/maven/**")
        exclude("META-INF/versions/**")

        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    from(arrayOf("LICENSE.txt", "NOTICE.txt")) {
        into("META-INF/")
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release.set(17)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmToolchain(17)
    }
}

