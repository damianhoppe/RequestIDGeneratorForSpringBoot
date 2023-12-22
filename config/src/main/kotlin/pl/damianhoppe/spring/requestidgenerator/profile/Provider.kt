package pl.damianhoppe.spring.requestidgenerator.profile

/**
 * An annotation to mark an argument-free method with the provider of the object it returns.
 *
 * Used to identify providers in configuration profiles, which are scanned by [ConfigurationProfileUtils]
 *
 * @see ConfigurationProfile
 * @see ConfigurationProfileUtils
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Provider
