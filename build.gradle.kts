plugins {
    kotlin("jvm") version "1.9.0-RC"
}

group = "com.akkih"
version = "1.0"

repositories {
    mavenCentral()
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    compileOnly("org.spigotmc:spigot-api:1.20-R0.1-SNAPSHOT")
}