package co.lopun.playground

import co.lopun.playground.dataTypes.numbers
import co.lopun.playground.fx.*
import co.lopun.playground.optics.atTutorial
import co.lopun.playground.optics.eachTutorial
import co.lopun.playground.optics.indexTutorial
import co.lopun.playground.syntax.curryAndPartial
import co.lopun.playground.optics.opticsGeneratedDsl

infix fun (() -> Unit).runWithName(name: String) {
    println("*start of $name")
    this()
    println("*end of $name\n")
}

fun main() {
    ::curryAndPartial runWithName "curryAndPartial"
    ::opticsGeneratedDsl runWithName "opticsGeneratedDSl"
    ::eachTutorial runWithName "eachTutorial"
    ::atTutorial runWithName "atTutorial"
    ::indexTutorial runWithName "indexTutorial"
    ::sideEffectsTutorial runWithName "sideEffectsTutorial"
    ::dispatchersAndContexts runWithName "dispatchersAndContexts"
    ::fibersTutorial runWithName "fibersTutorial"
    ::perMapNTutorial runWithName "perMapNTutorial"
    ::fxForOption runWithName "fxForOption"
    ::justTutorial runWithName "justTutorial"
    ::numbers runWithName "numbers"
}