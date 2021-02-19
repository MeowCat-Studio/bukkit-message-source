/**
 * Copyright 2020-2021 Meowcat Studio <studio@meowcat.org> and contributors.
 *
 * Licensed under the GNU Lesser General Public License version 3,
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *       https://opensource.org/licenses/LGPL-3.0
 */

import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
plugins {
   java
   kotlin("jvm") version "1.4.30"
   id("com.github.johnrengelman.shadow") version "5.2.0"
}

group = "org.meowcat"
version = "0.0.1"
val mccoroutine = "0.0.6"
repositories {
   mavenCentral()
   jcenter()
   maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
   maven("https://oss.sonatype.org/content/groups/public/")
   maven("https://maven.aura-dev.team/repository/auradev-releases/")
   maven("https://jitpack.io")
}

tasks.withType<ShadowJar> {
   exclude(
      "README.md",
      "module-info.class",
      "LICENSE",
      "Empty.class",
      "DebugProbesKt.bin",
      "CHANGELOG.md"
   )
   minimize {
      exclude(dependency(Dependency.Okhttp))
      exclude(dependency(Dependency.Ktor.Client.CIO))
      exclude(dependency(Dependency.Ktor.Client.WebSocket))
      exclude(dependency("com.github.shynixn.mccoroutine:mccoroutine-bukkit-core:$mccoroutine"))
      exclude(dependency("team.aura_dev.lib.slf4j-plugin.spigot:slf4j-plugin-spigot:1.2.0.39:1.7.25"))
   }
}
val compileKotlin: org.jetbrains.kotlin.gradle.tasks.KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = "1.8"
dependencies {

   api("org.spigotmc:spigot-api:1.16.3-R0.1-SNAPSHOT") {
      isTransitive = false
   }
   compileOnly("net.md-5:bungeecord-chat:1.16-R0.3")

   // websocket
   implementation("com.github.meowcat-studio:message-forwarding-client:0.0.1")
   implementation("com.github.meowcat-studio:handy-dandy:0.0.5")

   implementation(Dependency.Ktor.Client.WebSocket)
   implementation(Dependency.Ktor.Client.CIO)
   implementation(Dependency.Okhttp)

   // kotlin
   implementation(Dependency.Kotlin.StdLib)
   implementation(Dependency.KotlinX.Coroutine)

   // jackson
   implementation(Dependency.Jackson.Core)
   implementation(Dependency.Jackson.DataBind)
   implementation(Dependency.Jackson.Annotations)
   implementation(Dependency.Jackson.KotlinModule)

   // coroutine
   implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-api:$mccoroutine")
   implementation("com.github.shynixn.mccoroutine:mccoroutine-bukkit-core:$mccoroutine")

   // logging
   implementation("io.github.microutils:kotlin-logging-jvm:2.0.2")
   implementation("org.slf4j:slf4j-api:1.7.30")
   implementation("team.aura_dev.lib.slf4j-plugin.spigot:slf4j-plugin-spigot:1.2.0.39:1.7.25") {
      isTransitive = false
   }
}

object Dependency {
   object Kotlin {
      private const val group = "org.jetbrains.kotlin"
      const val StdLib = "$group:kotlin-stdlib:${Versions.Kotlin}"
      const val StbLib7 = "$group:kotlin-stdlib-jdk7:${Versions.Kotlin}"
      const val StbLib8 = "$group:kotlin-stdlib-jdk8:${Versions.Kotlin}"
      const val Reflect = "$group:kotlin-reflect:${Versions.Kotlin}"
   }
   object KotlinX {
      private const val group = "org.jetbrains.kotlinx"
      const val Coroutine = "$group:kotlinx-coroutines-core:${Versions.Coroutine}"
   }
   object Ktor {
      private const val group = "io.ktor"
      const val Core = ""
      object Client {
         const val WebSocket = "$group:ktor-client-websockets:${Versions.Ktor}"
         const val CIO = "$group:ktor-client-cio:${Versions.Ktor}"
      }
   }
   object Jackson {
      private const val group = "com.fasterxml.jackson"
      const val Core = "$group.core:jackson-core:${Versions.Jackson}"
      const val DataBind = "$group.core:jackson-databind:${Versions.Jackson}"
      const val Annotations = "$group.core:jackson-annotations:${Versions.Jackson}"
      const val KotlinModule = "$group.module:jackson-module-kotlin:${Versions.Jackson}"
   }
   const val Okhttp = "com.squareup.okhttp3:okhttp:${Versions.Okhttp}"
   const val Slf4j = "org.slf4j:slf4j-api:${Versions.Slf4j}"
}
object Versions {
   const val Kotlin = "1.4.30"
   const val Okhttp = "4.9.0"
   const val Ktor = "1.5.0"
   const val Coroutine = "1.4.1"
   const val Jackson = "2.12.1"
   const val Slf4j = "1.7.30"
   const val Mirai = "2.3.2"
   const val ShadowJar = "6.1.0"
   const val Vertx = "4.0.0"
}
