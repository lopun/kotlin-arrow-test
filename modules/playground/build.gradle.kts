import co.lopun.gradle.plugin.arrow
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("kapt")
    idea
}

apply(plugin = "kotlin-kapt")

tasks.withType<KotlinCompile>().all {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

repositories {
    mavenCentral()
    jcenter()
    maven { setUrl("https://dl.bintray.com/arrow-kt/arrow-kt/") }
    maven { setUrl("https://oss.jfrog.org/artifactory/oss-snapshot-local/") }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation(arrow("core"))
    implementation(arrow("fx"))
    implementation(arrow("optics"))
    implementation(arrow("syntax"))
    kapt(arrow(module = "meta"))
}

// Arrow Settings
// http://www.smartjava.org/content/kotlin-arrow-typeclasses/
idea {
    module {
        sourceDirs.addAll(
            files(
                "build/generated/source/kapt/main",
                "build/generated/source/kapt/debug",
                "build/generated/source/kapt/release",
                "build/generated/source/kaptKotlin/main",
                "build/generated/source/kaptKotlin/debug",
                "build/generated/source/kaptKotlin/release",
                "build/tmp/kapt/main/kotlinGenerated"
            )
        )
        generatedSourceDirs.addAll(
            files(
                "build/generated/source/kapt/main",
                "build/generated/source/kapt/debug",
                "build/generated/source/kapt/release",
                "build/generated/source/kaptKotlin/main",
                "build/generated/source/kaptKotlin/debug",
                "build/generated/source/kaptKotlin/release",
                "build/tmp/kapt/main/kotlinGenerated"
            )
        )
    }
}

