package pl.damianhoppe.reqidgen.sample.mainserver

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class MainController {

    val logger: Logger = LoggerFactory.getLogger(this.javaClass)

    @Autowired
    lateinit var api: SecondServerApi

    @GetMapping("")
    fun index(): Any? {
        logger.info("Hello world!")
        return "Hello world!"
    }

    @GetMapping("/secondServer")
    fun secondServerFeign(): Any? {
        logger.info("fetching data from the second service using feign library")
        return api.hello()
    }
}