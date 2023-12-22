package pl.damianhoppe.spring.requestidgenerator.configuration.profiles

import pl.damianhoppe.spring.requestidgenerator.RIdGProperties
import pl.damianhoppe.spring.requestidgenerator.profile.ConfigurationProfile
import pl.damianhoppe.spring.requestidgenerator.enum.RequestIdProvidingStrategy
import pl.damianhoppe.spring.requestidgenerator.profile.Provider


/**
 * Configuration profile for servers can receive requests from users and other
 * applications - request IDs are never read from the request by default
 */
@ConfigurationProfile("external")
class ExternalConfigurationProfile {

    @Provider
    fun externalDefaultProperties(): RIdGProperties {
        return RIdGProperties().apply {
            this.requestId.providingStrategy = RequestIdProvidingStrategy.GENERATE
        }
    }
}