/*
 * Copyright (C) Riiid, Inc - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Jaewe Heo <jwheo@riiid.co>, 8.2019
 */

import co.lopun.gradle.plugin.kotlintest
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformJvmPlugin
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

subprojects {
    apply<KotlinPlatformJvmPlugin>()

    tasks {
        withType<KotlinCompile>().all {
            kotlinOptions.jvmTarget = "1.8"
        }

        withType<Test>().all {
            testLogging.showStandardStreams = true
            testLogging.exceptionFormat = TestExceptionFormat.FULL

            @Suppress("UnstableApiUsage")
            useJUnitPlatform()
        }
    }

    dependencies {
        "implementation"(kotlin("stdlib-jdk8"))
        "testImplementation"(kotlintest())
    }
}
