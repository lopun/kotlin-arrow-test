package co.lopun.playground.fx

import arrow.core.*
import arrow.core.extensions.fx
import arrow.unsafe
import kotlinx.coroutines.runBlocking

fun fxForOption() {
    val result = Option.fx {
        val (one) = Option(1)
        val (two) = Option(one + one)
        two
    }

    unsafe { runBlocking { println(result.getOrElse { null }) } }
}

fun justTutorial() {
    val shouldNotBe1: (oneOrOther: Int) -> Option<Int> = { if (it == 1) None else Option.just(it) }
    val oneResult = Option.just(1).flatMap(shouldNotBe1).getOrElse { null }
    val twoResult = Option.just(2).flatMap(shouldNotBe1).getOrElse { null }
    println(oneResult)
    println(twoResult)
}

