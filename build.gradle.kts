plugins {
    kotlin("jvm") version "1.3.30" apply false
}

val projectGroup: String by project
val projectVersion: String by project

subprojects {
    group = projectGroup
    version = projectVersion
    repositories {
        jcenter()
    }
}
