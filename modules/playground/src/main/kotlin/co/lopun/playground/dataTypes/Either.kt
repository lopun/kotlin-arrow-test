package co.lopun.playground.dataTypes

import arrow.core.*
import arrow.core.extensions.fx

// Use Either Style Instead Of Exception Style
fun exceptionStyle() {
    fun parse(s: String): Int =
        if (s.matches(Regex("-?[0-9]+"))) s.toInt()
        else throw NumberFormatException("$s is not a valid integer")

    fun reciprocal(i: Int): Double =
        if (i == 0) throw IllegalArgumentException("Cannot take reciprocal of 0.")
        else 1.0 / i
}

sealed class CustomError {
    object NotANumber : Error()
    object NoZeroReciprocal : Error()
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

    // 혹은 sealed class를 활용해서 ADT스타일로도 가능하다
    // fun parse(s: String): Either<CustomError, Int> =
    //     if (s.matches(Regex("-?[0-9]+"))) Either.Right(s.toInt())
    //     else Either.Left(CustomError.NotANumber)
    //
    // fun reciprocal(i: Int): Either<CustomError, Double> =
    //     if (i == 0) Either.Left(CustomError.NoZeroReciprocal)
    //     else Either.Right(1.0 / i)
    //
    // fun stringify(d: Double): String = d.toString()
    //
    // fun getReciprocalFromString(s: String): Either<CustomError, String> =
    //     parse(s).flatMap { reciprocal(it) }.map { stringify(it) }
}

fun eitherSyntax() {
    val r: Either<Int, Int> = Either.Right(7)
    r.mapLeft { it + 1 }.let(::println)
    val l: Either<Int, Int> = Either.Left(7)
    l.mapLeft { it + 1 }.let(::println)
    r.swap().let(::println)

    // extension function style
    val R7 = 7.right()
    val Lhello = "hello".left()

    // Either Monad에서는 L이 비정상적인 값, R이 정상적인 값을 의미한다
    // ex) cond
    Either.cond(true, { 42 }, { "Error" }) // Right(b=42)
    Either.cond(false, { 42 }, { "Error" }) // Left(a=Error)

    // ex) fold - 왼쪽 lambda가 L Map Func, 오른쪽 람다가 R Map Func
    val x: Either<Int, Int> = 7.right()
    x.fold({ it + 1 }, { it + 3 }).let(::println) // 10
    val y: Either<Int, Int> = 7.left()
    y.fold({ it + 1 }, { it + 3 }).let(::println) // 8
}

fun getOrHandleTutorial() {
    // getOrHandle함수는 Left의 값을 Right로 안전하게 매핑할 수 있는 방법을 제공한다
    val r: Either<Throwable, Int> = Either.Left(NumberFormatException())
    val httpStatusCode = r.getOrHandle { throwable ->
        when (throwable) {
            is NumberFormatException -> 400
            else -> 500
        }
    } // 400
}

fun leftOrRightIfNull() {
    // 뒤 블록은 default값
    Right(12).leftIfNull { -1 } // Right(b=12)
    Right(null).leftIfNull { -1 } // Left(a=-1)
    "value".rightIfNull { "left" } // Right(b=value)
    null.rightIfNull { "left" } // Left(a=left)
}

fun functorAndApplicativeAndMonad() {
    // Functor Ex
    Right(1).map { it + 1 }

    // Applicative Ex
    Either.fx<Int, Int> {
        val (a) = Either.Right(1)
        val (b) = Either.Right(1 + a)
        val (c) = Either.Right(1 + b)
        a + b + c
    } // Right(6)
}