package pl.damianhoppe.reqidgen.sample.mainserver

import org.springframework.boot.autoconfigure.web.ServerProperties
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.stereotype.Component
import pl.damianhoppe.spring.requestidgenerator.RIdGConfigHolder
import pl.damianhoppe.spring.requestidgenerator.core.error.WithIdErrorController

@Component
class ErrorController(
    errorAttributes: ErrorAttributes,
    serverProperties: ServerProperties,
    config: RIdGConfigHolder,
) : WithIdErrorController(errorAttributes, serverProperties, config)