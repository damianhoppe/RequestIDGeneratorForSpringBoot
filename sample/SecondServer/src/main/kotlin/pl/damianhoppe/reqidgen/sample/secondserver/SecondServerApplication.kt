package pl.damianhoppe.reqidgen.sample.secondserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import pl.damianhoppe.spring.requestidgenerator.EnableRequestIdGenerator

@SpringBootApplication
@EnableRequestIdGenerator
class MainServerApplication

fun main(args: Array<String>) {
    runApplication<MainServerApplication>(*args)
}