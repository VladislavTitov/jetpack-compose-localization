import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}

repositories {
    maven("https://plugins.gradle.org/m2/")
    mavenCentral()
    google()
}

dependencies {
    val kotlinVersion = "1.5.31"
    val buildToolsVersion = "7.0.3"

    implementation(kotlin("stdlib-jdk8", kotlinVersion))

    compileOnly(gradleApi())
    api(kotlin("gradle-plugin", kotlinVersion))

    implementation("com.android.tools.build:gradle:$buildToolsVersion")
    implementation("com.android.tools.build:gradle-api:$buildToolsVersion")
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions {
    jvmTarget = "1.8"
}

gradlePlugin {
    (plugins) {

    }
}