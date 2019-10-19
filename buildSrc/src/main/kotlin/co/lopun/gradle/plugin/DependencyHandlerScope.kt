package co.lopun.gradle.plugin

import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.arrow(
    module: String,
    version: String = Ver.arrow
): Any = "io.arrow-kt:arrow-$module:$version"

fun DependencyHandlerScope.kotlintest(
    version: String = Ver.kotlintest
): Any = "io.kotlintest:kotlintest-runner-junit5:$version"