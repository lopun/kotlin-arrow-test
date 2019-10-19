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
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation(arrow("fx"))
    implementation(arrow("optics"))
    implementation(arrow("syntax"))
//    kapt(arrow(module = "meta"))
}
