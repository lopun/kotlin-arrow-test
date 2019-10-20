package co.lopun.playground

import co.lopun.playground.samples.fx.*
import co.lopun.playground.samples.optics.atTutorial
import co.lopun.playground.samples.optics.eachTutorial
import co.lopun.playground.samples.optics.indexTutorial
import co.lopun.playground.samples.syntax.curryAndPartial
import co.lopun.playground.samples.optics.opticsGeneratedDsl

fun main() {
    println("=== curryAndParital ===")
    curryAndPartial()
    println("=== opticsGeneratedDsl ===")
    opticsGeneratedDsl()
    println("=== eachTutorial ===")
    eachTutorial()
    println("=== atTutorial ===")
    atTutorial()
    println("=== indexTutorial ===")
    indexTutorial()
    println("=== sideEffectsTutorial ===")
    sideEffectsTutorial()
    println("=== dispatchersAndContexts ===")
    dispatchersAndContexts()
    println("=== fibersTutorial ===")
    fibersTutorial()
    println("=== perMapNTutoiral ===")
    perMapNTutorial()
    println("=== fxForOption ===")
    fxForOption()
    println("=== justTutorial ===")
    justTutorial()
}