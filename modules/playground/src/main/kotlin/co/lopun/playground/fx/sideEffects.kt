package co.lopun.playground.fx

import arrow.fx.IO
import arrow.fx.extensions.fx
import arrow.fx.extensions.io.unsafeRun.runBlocking
import arrow.unsafe

// Conclusion In Docs - 한번 읽어보기
// Complementing the Kotlin Coroutines library, Arrow Fx adds an extra layer of safety to concurrent and asynchronous programming so you’re well aware of where effects are localized in your apps.
// It does this by empowering polymorphic programs that can be interpreted untouched, preserving the same declaration in multiple popular frameworks such as Rx2, Reactor, etc.
// 대충 요약하면 fx는 추가적인 추상 레이어를 제공함으로써 병렬/비동기 작업들에서 일어나는 side effect를 어디서 일어나는지 알고 실행할 수 있도록 도와준다는 설명인것같다

fun sideEffectsTutorial() {
    suspend fun sayHello(): Unit = println("Hello World!")

    suspend fun sayGoodBye(): Unit = println("Good bye World!")

    suspend fun suspendComposition() {
        sayHello()
        sayGoodBye()
    }

    fun lazyGreet(): IO<Unit> = IO.fx {
        val pureHello = effect { sayHello() }
        val pureGoodBye = effect { sayGoodBye() }
    }

    lazyGreet().unsafeRunSync() // Nothing Prints

    // !effect로 side effect를 실행할 수 있다
    fun nonLazyGreet(): IO<Unit> = IO.fx {
        val nonPureHello = !effect { sayHello() }
        val nonPureGoodBye = !effect { sayGoodBye() }
    }

    // 이부분은 확실하게 이해가 필요하다. 아직 완벽히 이해하지 못함
    nonLazyGreet().unsafeRunSync() // Executed - Synchronously Runs Outside Coroutine Context
    unsafe { nonLazyGreet() } // Not Executed - Suspended Functions Only Fun In Coroutine Context
    unsafe { runBlocking { nonLazyGreet() } } // Executed - Suspended Functions Automatically Runs In Coroutine Context
}