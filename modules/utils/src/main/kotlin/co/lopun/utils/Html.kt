package co.lopun.utils

import arrow.syntax.function.*

val span = { className: String, body: String -> "<span class=\"$className\">$body</span>" }
val strong = { body: String -> "<strong>$body</span>" }
val yellowSpan = span(p2 = "yellow")
val greenSpan = span(p2 = "green")
