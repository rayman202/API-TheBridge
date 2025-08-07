plugins {
    `java-library`
    `maven-publish`
    kotlin("jvm") version "1.9.10"
}

group = "com.github.rayman202"
version = "1.0.0"
repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.8.8-R0.1-SNAPSHOT")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
    repositories {
        mavenLocal() // ¡Esta es la línea que necesitas agregar!
    }
}

