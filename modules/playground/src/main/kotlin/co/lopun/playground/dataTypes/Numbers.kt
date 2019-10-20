package co.lopun.playground.dataTypes

import arrow.core.extensions.show

fun numbers() {
    Int.show().run { 1.show() }.also(::println)
}
