import io.izzel.taboolib.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    id("io.izzel.taboolib") version "2.0.18"
}

taboolib {
    relocate("kotlinx.serialization", "kotlinx163.serialization")

    env {
        // 安装模块
        install(
            Basic,
            Bukkit,
            Kether,
            BukkitHook,
            BukkitNMS,
            BukkitNMSUtil,
            DatabasePlayer,
            Metrics,
            I18n,
            BukkitUI,
            JavaScript,
        )
        install("platform-bukkit-impl")
    }
    version { taboolib = "6.2.0-beta15" }
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://repo.rosewooddev.io/repository/public/")
}

dependencies {
    compileOnly("ink.ptms.core:v12101:12101:mapped")
    compileOnly("ink.ptms.core:v12101:12101:universal")
    compileOnly("ink.ptms:nms-all:1.0.0")

    compileOnly("io.netty:netty-all:4.1.106.Final")
    compileOnly("com.google.code.gson:gson:2.8.9")
    compileOnly("com.google.guava:guava:32.0.0-android")
    compileOnly("com.mojang:brigadier:1.0.18")

    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-core-jvm:1.6.3")
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json-jvm:1.6.3")
    compileOnly("net.kyori:adventure-api:4.15.0")
    compileOnly("net.kyori:adventure-platform-bukkit:4.3.2")
    compileOnly("net.kyori:adventure-text-minimessage:4.12.0")
    compileOnly("ink.ptms.adyeshach:all:2.0.0-snapshot-36")
    compileOnly("org.black_ixx:playerpoints:3.1.1")
    compileOnly("com.github.oraxen:oraxen:1.170.0")
    compileOnly("ink.ptms:Zaphkiel:2.0.14")


    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree("libs"))
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = listOf(
            "-Xjvm-default=all",
            "-Xextended-compiler-checks",
            "-Xskip-prerelease-check",
            "-Xallow-unstable-dependencies"
        )
    }
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}