package co.lopun.playground

import co.lopun.playground.dataTypes.*
import co.lopun.playground.fx.*
import co.lopun.playground.optics.atTutorial
import co.lopun.playground.optics.eachTutorial
import co.lopun.playground.optics.indexTutorial
import co.lopun.playground.syntax.curryAndPartial
import co.lopun.playground.optics.opticsGeneratedDsl

infix fun (() -> Unit).runWithBlock(name: String) {
    println("*start of $name")
    this()
    println("*end of $name\n")
}

fun main() {
    ::curryAndPartial runWithBlock "curryAndPartial"
    ::opticsGeneratedDsl runWithBlock "opticsGeneratedDSl"
    ::eachTutorial runWithBlock "eachTutorial"
    ::atTutorial runWithBlock "atTutorial"
    ::indexTutorial runWithBlock "indexTutorial"
    ::sideEffectsTutorial runWithBlock "sideEffectsTutorial"
    ::dispatchersAndContexts runWithBlock "dispatchersAndContexts"
    ::fibersTutorial runWithBlock "fibersTutorial"
    ::perMapNTutorial runWithBlock "perMapNTutorial"
    ::fxForOption runWithBlock "fxForOption"
    ::justTutorial runWithBlock "justTutorial"
    ::show runWithBlock "show"
    ::eq runWithBlock "eq"
    ::semigroup runWithBlock "semigroup"
    ::monoid runWithBlock "monoid"
    ::eitherStyle runWithBlock "eitherStyle"
}