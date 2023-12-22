package pl.damianhoppe.spring.requestidgenerator.configuration

import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import pl.damianhoppe.spring.requestidgenerator.RIdGProperties
import pl.damianhoppe.spring.requestidgenerator.config.RIdGConfig
import pl.damianhoppe.spring.requestidgenerator.subconfig.MainConfig

/**
 * Spring configuration for [MainConfig] bean
 */
@Configuration
class MainConfigBean {

    /**
     * Provide a prototype bean of [MainConfig]
     * to enable it to be loaded into the final configuration[RIdGConfig]
     */
    @Bean
    @Scope("prototype")
    fun defaultCoreConfig(app: ApplicationContext, properties: RIdGProperties): MainConfig {
        return MainConfig(app, properties)
    }
}