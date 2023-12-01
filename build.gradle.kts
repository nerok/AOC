plugins {
    kotlin("jvm") version "1.9.21"
    id("org.jlleitschuh.gradle.ktlint") version "11.6.1"
    application
}

group = "nerok.aoc"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

allprojects {
    group = "nerok.aoc"

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}
