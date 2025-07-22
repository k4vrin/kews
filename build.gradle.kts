import java.io.FileInputStream
import java.util.Properties

@Suppress("DSL_SCOPE_VIOLATION")

plugins {
    //trick: for the same plugin versions in all sub-modules
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.android).apply(false)
    alias(libs.plugins.kotlin.compose).apply(false)
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.kotlin.serialization).apply(false)
    alias(libs.plugins.jetbrains.compose).apply(false)
    alias(libs.plugins.buildKonfig).apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.layout.buildDirectory)
}


fun getProps(path: String): Properties {
    println("Current working dir: " + File(".").absolutePath)
    println("Exists? " + File(".configs/main/.secrets/secrets.properties").absolutePath + " --> " + File(".configs/main/.secrets/secrets.properties").exists())


    val props = Properties()
    FileInputStream(rootProject.file(path)).use {
        props.load(it)
    }
    return props
}

val configs = getProps(".configs/main/.secrets/secrets.properties")

// Set [configs], [signingProps] and [flavor] as extra properties.
ext["configs"] = configs