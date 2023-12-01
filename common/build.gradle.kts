plugins {
    kotlin("jvm") version "1.9.21"
    application
}

version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.20-1.0.14")
    testImplementation(kotlin("test"))
    testImplementation("io.strikt:strikt-core:0.34.0")
}

kotlin {
    jvmToolchain(20)
}
