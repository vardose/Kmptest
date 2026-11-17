import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties
import java.io.File

val localProperties = Properties().apply {
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        load(file.inputStream())
    }
}
val apiKey = localProperties.getProperty("WEATHER_API_KEY", "b80967f0a6bd10d23e44848547b26550")

val generateApiKeyTask = tasks.register("generateApiKey") {
    val outputDir = layout.buildDirectory.dir("generated/apiKey")
    val apiKeyProvider = providers.provider { apiKey }
    outputs.dir(outputDir)
    inputs.property("apiKey", apiKeyProvider)
    doLast {
        val file = outputDir.get().file("com/example/kmptest/remote/ApiKey.kt").asFile
        file.parentFile.mkdirs()
        file.writeText("""
            package com.example.kmptest.remote
            internal const val API_KEY = "${apiKeyProvider.get()}"
        """.trimIndent())
    }
}

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization") version "2.4.0"
}

kotlin {
    jvm()
    android {
       namespace = "com.example.kmptest.shared"
       compileSdk = libs.versions.android.compileSdk.get().toInt()
       minSdk = libs.versions.android.minSdk.get().toInt()
       compilerOptions {
           jvmTarget = JvmTarget.JVM_11
       }
       androidResources {
           enable = true
       }
       withHostTest {
           isIncludeAndroidResources = true
       }
       withDeviceTestBuilder {
           sourceSetTreeName = "test"
       }.configure {
           instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
       }
    }
    sourceSets {
        named("commonMain") {
            kotlin.srcDir(generateApiKeyTask.map { it.outputs.files.singleFile })
        }
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.compose.uiTooling)
            implementation("io.ktor:ktor-client-okhttp:3.4.2")
            implementation("io.insert-koin:koin-android:4.2.2")
        }
        jvmMain.dependencies {
            //Client de requêtes spécifique au bureau sur JVM donc même qu'Android
            implementation("io.ktor:ktor-client-okhttp:3.4.2")
        }
        commonMain.dependencies {
            implementation("io.insert-koin:koin-core:4.2.2")
            implementation("io.insert-koin:koin-compose:4.2.2")
            implementation("io.insert-koin:koin-compose-viewmodel:4.2.2")
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            // (les interfaces en gros)
            implementation("io.ktor:ktor-client-core:3.4.2")
            //Intégration avec la bibliothèque de serialisation, gestion des headers
            implementation("io.ktor:ktor-client-content-negotiation:3.4.2")
            //Serialisation JSON
            implementation("io.ktor:ktor-serialization-kotlinx-json:3.4.2")
            //Pour le logger
            implementation("io.ktor:ktor-client-logging:3.4.2")

            //pour les icônes avec org.jetbrains spécifique à KMP
            implementation("org.jetbrains.compose.material:material-icons-extended:1.7.3")


            //ImageLoader (identique)
            implementation("io.coil-kt.coil3:coil-network-ktor3:3.2.0")
            implementation("io.coil-kt.coil3:coil-compose:3.2.0")

            //Navigation 3 avec org.jetbrains spécifique à KMP
            implementation("org.jetbrains.androidx.navigation3:navigation3-ui:1.1.2")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        getByName("androidDeviceTest").dependencies {
            implementation(libs.androidx.testRunner)
        }
    }
}

dependencies {
    androidRuntimeClasspath(libs.compose.uiTooling)
}