package pl.damianhoppe.spring.requestidgenerator.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import pl.damianhoppe.spring.requestidgenerator.RIdGProperties
import pl.damianhoppe.spring.requestidgenerator.config.RIdGConfig
import pl.damianhoppe.spring.requestidgenerator.subconfig.CoreConfig
import pl.damianhoppe.spring.requestidgenerator.core.filter.DefaultRequestIdGeneratorFilter
import pl.damianhoppe.spring.requestidgenerator.core.idgenerator.RequestIdGeneratorUUID
import pl.damianhoppe.spring.requestidgenerator.core.servletrequestlistener.DefaultReqIdGenServletRequestListener

/**
 * Spring configuration for [CoreConfig] bean
 */
@Configuration
class CoreConfigBean {

    /**
     * Provide a prototype bean of [CoreConfig]
     * to enable it to be loaded into the final configuration[RIdGConfig]
     */
    @Bean
    @Scope("prototype")
    fun defaultFilterConfig(properties: RIdGProperties): CoreConfig {
        return CoreConfig(properties, RequestIdGeneratorUUID(), DefaultRequestIdGeneratorFilter.Builder(), DefaultReqIdGenServletRequestListener.Builder())
    }
}