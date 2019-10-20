package co.lopun.playground.dataTypes

import arrow.core.*
import arrow.core.extensions.eq
import arrow.core.extensions.list.foldable.combineAll
import arrow.core.extensions.list.foldable.foldMap
import arrow.core.extensions.monoid
import arrow.core.extensions.option.semigroup.semigroup
import arrow.core.extensions.semigroup
import arrow.core.extensions.show
import arrow.typeclasses.Eq
import arrow.typeclasses.Monoid
import arrow.typeclasses.Show

fun show() {
    // show는 무엇이든 String화 시킨다. toString과 비슷함
    Int.show().run { 1.show() }.let(::println)
    Show.any().run { None.show() }.let(::println)
    Show.any().run { Option.just(1).show() }.let(::println)

    // custom show
    class Person(val firstName: String, val lastName: String)

    val showPerson = { person: Person -> Show<Person> { "Hello $firstName $lastName" }.run { person.show() } }
    showPerson(Person("Jihun", "Ko")).let(::println)
}

fun eq() {
    String.eq().run { "1".eqv("2") }.let(::println)
    Int.eq().run { 1.eqv(2) }.let(::println)
    Int.eq().run { 1.neqv(2) }.let(::println)
    Eq.any().run { Some(1).eqv(Option.just(1)) }.let(::println)
}

fun order() {
    // TODO
}

fun semigroup() {
    // combinable이라고 생각하면 편할듯
    Int.semigroup().run { 1.combine(2) }.let(::println)
    Option.semigroup(Int.semigroup()).run { Option(1).combine(Option(2)) }.let(::println)
}

fun monoid() {
    // semigroup에 empty method가 추가된것! -> empty method는 다른 instance와 combine되었을때 무조건 다른 instance를 리턴하는 항등원(?)이어야한다
    listOf(1, 2, 3, 4, 5).k().combineAll(Int.monoid()).let(::println)
    listOf(1, 2, 3, 4, 5).combineAll(Int.monoid()).let(::println)
    listOf(1, 2, 3, 4, 5).k().foldMap(Int.monoid(), ::identity).let(::println)


    // custom monoid
    fun <A, B> monoidTuple(MA: Monoid<A>, MB: Monoid<B>): Monoid<Tuple2<A, B>> = object : Monoid<Tuple2<A, B>> {
        override fun Tuple2<A, B>.combine(b: Tuple2<A, B>): Tuple2<A, B> {
            val (xa, xb) = this
            val (ya, yb) = b
            return Tuple2(MA.run { xa.combine(ya) }, MB.run { xb.combine(yb) })
        }

        override fun empty(): Tuple2<A, B> = Tuple2(MA.empty(), MB.empty())
    }

    val M = monoidTuple(Int.monoid(), String.monoid())
    val list = listOf(1, 1).k()

    list.foldMap(M) { n: Int ->
        Tuple2(n, n.toString())
    }.let(::println)
    // Tuple2(0, "") -> Tuple2(1, "1") -> Tuple(2, "11")
}