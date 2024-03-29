package co.lopun.playground.optics

import arrow.core.ListK
import arrow.core.MapK
import arrow.optics.optics

@optics
data class Street(val number: Int, val name: String) {
    companion object
}

@optics
data class Address(val city: String, val street: Street) {
    companion object
}

@optics
data class Company(val name: String, val address: Address) {
    companion object
}

@optics
data class Employee(val name: String, val company: Company?) {
    companion object
}

@optics
data class Employees(val employees: ListK<Employee>) {
    companion object
}

@optics data class Db(val content: MapK<Int, String>) {
    companion object
}
