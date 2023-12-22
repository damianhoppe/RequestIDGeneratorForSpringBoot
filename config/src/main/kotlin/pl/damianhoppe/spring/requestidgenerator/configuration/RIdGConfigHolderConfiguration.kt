package pl.damianhoppe.spring.requestidgenerator.configuration

import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.NoSuchBeanDefinitionException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Scope
import pl.damianhoppe.spring.requestidgenerator.RIdGConfigHolder
import pl.damianhoppe.spring.requestidgenerator.config.RIdGConfig
import pl.damianhoppe.spring.requestidgenerator.config.RIdGConfigurer
import pl.damianhoppe.spring.requestidgenerator.config.RIdGSubConfig

/**
 * Spring configuration providing a config configurer[RIdGConfigurer] object
 * and the final configuration[RIdGConfig] via container[RIdGConfigHolder].
 */
@Configuration
class RIdGConfigHolderConfiguration {
    private val log = LogFactory.getLog(javaClass)

    @Autowired
    lateinit var app: ApplicationContext

    /**
     * Provide prototype bean of [RIdGConfigurer] to allow to customize config by user.
     */
    @Bean
    @Scope("prototype")
    fun requestIdGeneratorConfigurer(): RIdGConfigurer {
        return RIdGConfigurer(app.getBeansOfType(RIdGSubConfig::class.java).mapKeys { it.value.javaClass })
    }

    /**
     * Loads [RIdGConfig] specified by user or default config
     * and stores it in [RIdGConfigHolder] instance.
     */
    @Bean
    fun defaultRIdGConfig(): RIdGConfigHolder {
        val config = try {
            app.getBean(RIdGConfig::class.java).also {
                log.debug("Found existing custom configuration")
            }
        }catch (e: NoSuchBeanDefinitionException) {
            log.debug("Configuration not found, loading default")
            requestIdGeneratorConfigurer().build()
        }
        return RIdGConfigHolder(config)
    }
}