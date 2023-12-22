package pl.damianhoppe.reqidgen.sample.secondserver

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class MainController {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)
    @GetMapping("/hello")
    fun hello(): Any? {
        logger.info("Request received.")
        return "Hi ;)"
    }
}