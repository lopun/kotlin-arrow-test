package co.lopun.playground.samples.optics

import arrow.core.ListK
import arrow.core.MapK
import arrow.core.k
import arrow.optics.dsl.at
import arrow.optics.dsl.every
import arrow.optics.dsl.index
import arrow.optics.dsl.some
import arrow.optics.extensions.listk.each.each
import arrow.optics.extensions.listk.index.index
import arrow.optics.extensions.mapk.at.at
import arrow.optics.optics

fun opticsGeneratedDsl() {
    val john = Employee("John Doe", Company("Kategory", Address("Functional city", Street(42, "lambda street"))))

    val modifiedJohn = Employee.company.address.street.name.modify(john, String::toUpperCase)

    println(john.toString())
    println(modifiedJohn.toString())
//  Employee(name=John Doe, company=Company(name=Kategory, address=Address(city=Functional city, street=Street(number=42, name=lambda street))))
//  Employee(name=John Doe, company=Company(name=Kategory, address=Address(city=Functional city, street=Street(number=42, name=LAMBDA STREET))))
}

fun eachTutorial() {
    val john = Employee("John Doe", Company("Kategory", Address("Functional city", Street(42, "lambda street"))))
    val jane = Employee("Jane Doe", Company("Kategory", Address("Functional City", Street(42, "lambda street"))))
    val employees = Employees(listOf(john, jane).k())
    val modifiedEmployees =
        Employees.employees.every(ListK.each()).company.address.street.name.modify(employees, String::capitalize)

//  Second Version - Scoping
//  val modifiedEmployees = ListK.each<Employee>().run {
//    Employees.employees.every.company.address.steet.name.modify(employees, String::capitalize)
//  }

    println(employees.toString())
    println(modifiedEmployees.toString())
//  Employees(employees=ListK(list=[Employee(name=John Doe, company=Company(name=Kategory, address=Address(city=Functional city, street=Street(number=42, name=lambda street)))), Employee(name=Jane Doe, company=Company(name=Kategory, address=Address(city=Functional City, street=Street(number=42, name=lambda street))))]))
//  Employees(employees=ListK(list=[Employee(name=John Doe, company=Company(name=Kategory, address=Address(city=Functional city, street=Street(number=42, name=Lambda street)))), Employee(name=Jane Doe, company=Company(name=Kategory, address=Address(city=Functional City, street=Street(number=42, name=Lambda street))))]))
}

fun atTutorial() {
    val db = Db(
        mapOf(
            1 to "one",
            2 to "two",
            3 to "three"
        ).k()
    )

    val reversed = Db.content.at(MapK.at(), 2).some.modify(db, String::reversed)
    println(reversed.toString())

//  Db(content=MapK(map={1=one, 2=owt, 3=three}))
}

fun indexTutorial() {
    val john = Employee("John Doe", Company("Kategory", Address("Functional city", Street(42, "lambda street"))))
    val jane = Employee("Jane Doe", Company("Kategory", Address("Functional City", Street(42, "lambda street"))))
    val employees = Employees(listOf(john, jane).k())
    val onlyJohnUpdated =
        Employees.employees.index(ListK.index(), 0).company.address.street.name.modify(employees, String::capitalize)
    println(onlyJohnUpdated.toString())
//  Employees(employees=ListK(list=[Employee(name=John Doe, company=Company(name=Kategory, address=Address(city=Functional city, street=Street(number=42, name=Lambda street)))), Employee(name=Jane Doe, company=Company(name=Kategory, address=Address(city=Functional City, street=Street(number=42, name=lambda street))))]))
}
