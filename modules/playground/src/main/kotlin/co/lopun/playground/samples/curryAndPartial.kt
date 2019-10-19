package co.lopun.playground.samples

import arrow.syntax.function.*
import co.lopun.utils.span
import co.lopun.utils.yellowSpan

fun curryAndPartial() {
    val curriedSpan = span.curried()
    curriedSpan("span-class")("Sample Span Body").let(::println)
    yellowSpan("Yellow Body").let(::println)
}

