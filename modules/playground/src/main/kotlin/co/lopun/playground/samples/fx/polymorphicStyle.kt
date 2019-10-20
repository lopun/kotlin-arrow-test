package co.lopun.playground.samples.fx

import arrow.Kind
import arrow.fx.IO
import arrow.fx.extensions.io.concurrent.concurrent
import arrow.fx.extensions.io.unsafeRun.unsafeRun
import arrow.fx.typeclasses.Concurrent
import arrow.fx.typeclasses.UnsafeRun
import arrow.unsafe

fun polymorphicStyleTutorial() {
    /* a side effect */
    val const = 1

    suspend fun sideEffect(): Int {
        println(Thread.currentThread().name)
        return const
    }

    /* for all `F` that provide an `Fx` extension define a program function */
    fun <F> Concurrent<F>.program(): Kind<F, Int> =
        this.fx.concurrent { !effect { sideEffect() } }

    /* for all `F` that provide an `UnsafeRun` extension define a main function */
    fun <F> UnsafeRun<F>.main(fx: Concurrent<F>): Int =
        unsafe { runBlocking { fx.program() } }

    /* Run program in the IO monad */
    fun main(args: Array<String>) {
        IO.unsafeRun().main(IO.concurrent())
    }
}