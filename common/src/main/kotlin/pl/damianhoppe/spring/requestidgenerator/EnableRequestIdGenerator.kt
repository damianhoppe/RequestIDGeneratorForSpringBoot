package pl.damianhoppe.spring.requestidgenerator

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.ComponentScans

/**
 * Loads the configuration of the request id generator and all its submodules.
 * To customize your configuration, please refer to the documentation for each submodule.
 */
@Retention
@Target(AnnotationTarget.CLASS)
@ComponentScans(ComponentScan("pl.damianhoppe.spring.requestidgenerator.configuration"))
annotation class EnableRequestIdGenerator