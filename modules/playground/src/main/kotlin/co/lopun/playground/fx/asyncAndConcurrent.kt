package co.lopun.playground.fx

import arrow.fx.IO
import arrow.fx.extensions.fx
import kotlinx.coroutines.newSingleThreadContext
import kotlin.coroutines.coroutineContext

// dispatchers().default() is an execution context that’s available to all concurrent data types,
// such as IO, that you can use directly on fx blocks.

fun dispatchersAndContexts() {
    val contextA = newSingleThreadContext("A")
    suspend fun printThreadName(): Unit = println(Thread.currentThread().name)

    val program = IO.fx {
        continueOn(contextA)
        !effect { printThreadName() }
        !effect { printThreadName() }

        continueOn(dispatchers().default())
        !effect { printThreadName() }
    }

    program.unsafeRunSync()
}

fun fibersTutorial() {
    suspend fun threadName(): String = Thread.currentThread().name

    val program = IO.fx {
        val fiberA = !effect { Pair(threadName(), coroutineContext) }.fork(dispatchers().default())
        val fiberB = !effect { Pair(threadName(), coroutineContext) }.fork(dispatchers().default())
        val threadA = !fiberA.join()
        val threadB = !fiberB.join()
        // 같은 쓰레드풀의 서로다른 worker로 동작
        // coroutineContext는 동일하다
        !effect { println(threadA) }
        !effect { println(threadB) }
    }

    program.unsafeRunSync()
}

fun perMapNTutorial() {
    suspend fun threadName(): String =
        Thread.currentThread().name

    data class ThreadInfo(
        val threadA: String,
        val threadB: String
    )

    val program = IO.fx {
        val (threadA: String, threadB: String) =
            !dispatchers().default().parMapN(
                effect { threadName() },
                effect { threadName() },
                ::ThreadInfo
            )
        !effect { println(threadA) }
        !effect { println(threadB) }
    }

    program.unsafeRunSync()
}