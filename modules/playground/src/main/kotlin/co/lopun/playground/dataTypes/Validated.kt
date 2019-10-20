package co.lopun.playground.dataTypes

import arrow.core.*

data class ConnectionParams(val url: String, val port: Int)

abstract class Read<A> {

    abstract fun read(s: String): Option<A>

    companion object {

        val stringRead: Read<String> =
            object : Read<String>() {
                override fun read(s: String): Option<String> = Option(s)
            }

        val intRead: Read<Int> =
            object : Read<Int>() {
                override fun read(s: String): Option<Int> =
                    if (s.matches(Regex("-?[0-9]+"))) Option(s.toInt()) else None
            }
    }
}

sealed class ConfigError {
    data class MissingConfig(val field: String) : ConfigError()
    data class ParseConfig(val field: String) : ConfigError()
}

data class Config(val map: Map<String, String>) {
    // Validated는 Either Monad의 Validation 특화된 버전이라고 보면 됨
    fun <A> parse(read: Read<A>, key: String): Validated<ConfigError, A> {
        val v = Option.fromNullable(map[key])
        return when (v) {
            is Some -> {
                val s = read.read(v.t)
                when (s) {
                    is Some -> s.t.valid()
                    is None -> ConfigError.ParseConfig(key).invalid()
                }
            }
            is None -> Validated.Invalid(ConfigError.MissingConfig(key)) // 이런 스타일도 있다는걸 보여주는 코드
        }
    }
}

fun <E, A, B, C> parallelValidate(
    v1: Validated<E, A>,
    v2: Validated<E, B>,
    f: (A, B) -> C
): Validated<NonEmptyList<E>, C> {
    return when {
        v1 is Validated.Valid && v2 is Validated.Valid -> Validated.Valid(f(v1.a, v2.a))
        // Nel = NonEmptyList
        v1 is Validated.Valid && v2 is Validated.Invalid -> v2.toValidatedNel()
        v1 is Validated.Invalid && v2 is Validated.Valid -> v1.toValidatedNel()
        v1 is Validated.Invalid && v2 is Validated.Invalid -> Validated.Invalid(NonEmptyList(v1.e, listOf(v2.e)))
        else -> throw IllegalStateException("Not possible value")
    }
}

fun validConfig() {
    val config = Config(mapOf("url" to "127.0.0.1", "port" to "1337"))

    val valid = parallelValidate(
        config.parse(Read.stringRead, "url"),
        config.parse(Read.intRead, "port")
    ) { url, port ->
        ConnectionParams(url, port)
    }
    valid
    // Valid(a=ConnectionParams(url=127.0.0.1, port=1337))
}

fun invalidConfig() {
    val config = Config(mapOf("url" to "127.0.0.1", "port" to "not a number"))

    val valid = parallelValidate(
        config.parse(Read.stringRead, "url"),
        config.parse(Read.intRead, "port")
    ) { url, port ->
        ConnectionParams(url, port)
    }
    valid
    // Invalid(e=NonEmptyList(all=[ParseConfig(field=port)]))
}
