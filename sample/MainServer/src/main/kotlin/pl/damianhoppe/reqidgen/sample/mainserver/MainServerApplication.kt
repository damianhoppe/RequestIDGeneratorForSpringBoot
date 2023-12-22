package pl.damianhoppe.reqidgen.sample.mainserver

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.ApplicationContext
import pl.damianhoppe.spring.requestidgenerator.RIdGProperties

@SpringBootApplication
@EnableFeignClients
class MainServerApplication {

    @Autowired
    lateinit var app: ApplicationContext

    @Autowired
    lateinit var properties: RIdGProperties
}

fun main(args: Array<String>) {
    runApplication<MainServerApplication>(*args)
}