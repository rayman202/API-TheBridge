plugins {
    `java-library`
    `maven-publish`
}

group = "com.github.rayman202"
version = "1.0.0"

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}
