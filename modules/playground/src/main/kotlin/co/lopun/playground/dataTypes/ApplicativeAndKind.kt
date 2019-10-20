package co.lopun.playground.dataTypes

import arrow.core.*
import arrow.core.extensions.list.apply.ap

// Kotlin에서는 type constructor을 지원하지 않기때문에 arrow에서는 Kind라는 interface로 대신한다.
// Kine는 Higher Kind를 의미하는데, 이는 type constructor랑 직접적으로 상호작용할 수 있는 언어의 기능을 의미하는 말이다.
// 더 자세한 설명은 https://arrow-kt.io/docs/patterns/glossary/#type-constructors를 참고하자!

// k()는 Kind로 변환해주는 함수이다.
// fix()는 Kind를 다시 기존 형태로 돌려주는 함수다

// ap func: Functor 리스트를 받아서 flatMap을 진행
fun applicativeExamples() {
    listOf("a", "b", "c").k().foldLeft("-> ") { x, y -> x + y }

    // 각 Lambda함수를 적용하고 flatMapping
    listOf(1, 2, 3).ap(listOf({ x: Int -> x + 10 }, { x: Int -> x * 2 })) // List(11,12,13,2,4,6)
    listOf(Some(1), None, Some(2)).ap(listOf({ x: Option<Int> -> x.getOrElse { null } })).filterNotNull().let(::println)
}