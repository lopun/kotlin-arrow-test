package co.lopun.playground.generic

import arrow.core.None
import arrow.core.Option
import arrow.core.Tuple2
import arrow.core.extensions.`try`.applicative.applicative
import arrow.core.extensions.option.applicative.applicative
import arrow.core.fix
import arrow.fx.IO
import arrow.fx.extensions.io.applicative.applicative
import arrow.fx.fix
import arrow.product

@product
data class Account(val balance: Int, val available: Int) {
    companion object
}

fun autoSemigroupAndMonoid() {
    // plus 등 main 함수들 autoGen
    Account(1000, 900) + Account(1000, 900)
    listOf(Account(1000, 900), Account(1000, 900)).combineAll()
    // to_ModelName_ autoGen
    Tuple2(1000, 900).toAccount()
}

fun codeGen() {
    // mapTo_ModelName_ authGen
    Option.applicative().run {
        mapToAccount(Option(1000), Option(900))
    }.fix() // Some(Account(balance = 1000, availbale = 900))
    Option.applicative().run {
        mapToAccount(Option(1000), None)
    }.fix() // None
    IO.applicative().run {
        mapToAccount(IO { 1000 }, IO { 900 })
    }.fix().unsafeRunSync()
}
