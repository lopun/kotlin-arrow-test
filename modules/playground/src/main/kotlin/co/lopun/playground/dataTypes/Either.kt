package co.lopun.playground.dataTypes

import arrow.core.Either
import arrow.core.flatMap

// Use Either Style Instead Of Exception Style
fun exceptionStyle() {
    fun parse(s: String): Int =
        if (s.matches(Regex("-?[0-9]+"))) s.toInt()
        else throw NumberFormatException("$s is not a valid integer")

    fun reciprocal(i: Int): Double =
        if (i == 0) throw IllegalArgumentException("Cannot take reciprocal of 0.")
        else 1.0 / i
}

fun eitherStyle() {
    fun parse(s: String): Either<NumberFormatException, Int> =
        if (s.matches(Regex("-?[0-9]+"))) Either.right(s.toInt())
        else Either.left(NumberFormatException("$s is not a valid integer"))

    fun reciprocal(i: Int): Either<IllegalArgumentException, Double> =
        if (i == 0) Either.left(IllegalArgumentException("Cannot take reciprocal of 0."))
        else Either.right(1.0 / i)

    fun stringify(d: Double): String = d.toString()

    fun getReciprocalFromString(s: String): Either<Exception, String> =
        parse(s).flatMap(::reciprocal).map(::stringify)

    getReciprocalFromString("Not a number").let(::println)
    getReciprocalFromString("1").let(::println)

    val getErrorStringOrvalue = { value: String ->
        val getReciprocalFromStringVal = getReciprocalFromString(value)
        when (getReciprocalFromStringVal) {
            is Either.Left -> when (getReciprocalFromStringVal.a) {
                is NumberFormatException -> "Not a number!"
                is IllegalArgumentException -> "Can't take reciprocal of 0!"
                else -> "Unknown error"
            }
            is Either.Right -> "Got reciprocal: ${getReciprocalFromStringVal.b}"
        }
    }

    getErrorStringOrvalue("Not a number").let(::println)
    getErrorStringOrvalue("0").let(::println)
    getErrorStringOrvalue("1").let(::println)
}