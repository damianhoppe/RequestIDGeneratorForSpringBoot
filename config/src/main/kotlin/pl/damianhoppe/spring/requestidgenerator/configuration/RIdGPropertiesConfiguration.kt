package pl.damianhoppe.spring.requestidgenerator.configuration

import org.apache.commons.logging.LogFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.util.ClassUtils
import pl.damianhoppe.spring.requestidgenerator.RIdGProperties
import pl.damianhoppe.spring.requestidgenerator.config.RIdGConfigurer
import pl.damianhoppe.spring.requestidgenerator.profile.*

/**
 * Spring configuration providing a property object[RIdGProperties]
 */
@Configuration
@PropertySource("classpath:/base-ridg.properties")
class RIdGPropertiesConfiguration {

    private val log = LogFactory.getLog(ClassUtils.getUserClass(this::class.java))

    @Autowired
    lateinit var app: ApplicationContext

    @Value("\${ridg.profile:}")
    lateinit var configurationProfile: String

    /**
     * Provides the correct property object based on the specified configuration
     * profile, or the default if one is not specified
     */
    @Bean
    fun requestIdGeneratorProperties(): RIdGProperties {
        assert(app.getBeanNamesForType(RIdGProperties::class.java).isEmpty()) {
            "Con not create RIdGProperties, because property object[RIdGProperties.class] exists in ApplicationContext. There should be only one instance of RIdGProperties, if you want to overwrite the default values, use the RIdGConfigurer[${RIdGConfigurer::class.java.name}] or use configuration profiles[${ConfigurationProfile::class.java.name}]".apply {
                log.warn(this)
            }
        }
        if(configurationProfile.isBlank() || configurationProfile.trim().equals("none", ignoreCase = true)) {
            log.debug("Loading default configuration")
            return defaultProperties()
        }
        log.debug("Loading configuration profile: $configurationProfile")
        return getCustomProperties()
    }

    /**
     * Returns the default properties if no profile has been matched
     */
    fun defaultProperties() = RIdGProperties()

    /**
     * Returns the default properties for the configuration profile specified by the name - property: *ridg.profile*
     * or default properties if the profile does not contain the appropriate properties definition
     *
     * @throws Exception If none is found for a specific profile name, multiple profiles will be found or other problems loading
     * the configuration or profile will occur
     */
    private fun getCustomProperties(): RIdGProperties {
        val configurationProfiles = findConfigurationProfileComponents(findBasePackages(app))
        val profileFound = configurationProfiles.findProfile(configurationProfile)
            ?: throw IllegalStateException("Not found configuration profile: \"$configurationProfile\"")
        log.debug("Found configuration profile: \"$configurationProfile\"")
        tryLoadPropertiesFromConfigurationProfileClass(profileFound.second)?.let {
            return it
        }
        log.info("Not found default properties definition in configuration profile - \"$configurationProfile\". Loading default properties.")
        return defaultProperties()
    }

    /**
     * Searches a pair of ConfigurationProfile and Class for specified profile name
     * @param name Profile name
     * @throws IllegalStateException if more configuration profiles are found for the given name
     */
    private fun Map<ConfigurationProfile, Class<*>>.findProfile(name: String): Pair<ConfigurationProfile, Class<*>>? {
        val matchedProfiles = this.filter {
            it.key.name == name
        }
        if(matchedProfiles.isEmpty()) {
            return null
        }
        val pair = matchedProfiles.entries.first().toPair()
        if(matchedProfiles.size > 1) {
            throw IllegalStateException("To profile name \"$name\" matched more multiple configuration profiles: ${matchedProfiles.keys.joinToString()}")
        }
        return pair
    }

    /**
     * Loading RIdProperties from class, if it contains the appropriate definition
     * @param clazz The class that should contain the definition of the
     * RIdGProperties object - method that return RIdGProperties and annotated with @[Provider]
     * @return Instance of RIdGProperties object or null if no definition is found
     */
    private fun tryLoadPropertiesFromConfigurationProfileClass(clazz: Class<*>): RIdGProperties? {
        return getObjectOfTypeFrom(createInstance(clazz, app), RIdGProperties::class.java)
    }
}