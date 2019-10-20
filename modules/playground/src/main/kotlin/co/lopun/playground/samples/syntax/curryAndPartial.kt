package co.lopun.playground.samples.syntax

import arrow.syntax.function.*

fun curryAndPartial() {
    val span = { className: String, body: String -> "<span class=\"$className\">$body</span>" }
    val curriedSpan = span.curried()
    curriedSpan("span-class")("Sample Span Body").let(::println)
//    <span class="span-class">Sample Span Body</span>
}

