package pl.damianhoppe.spring.requestidgenerator.profile

import pl.damianhoppe.spring.requestidgenerator.RIdGProperties

/**
 * An annotation specifying a named configuration profile that may contain definitions of other
 * objects(methods annotated with [Provider]) loaded when configuring modules.
 * For example, you can write the default properties by adding the following class:
 * ```
 * @ConfigurationProfile("test")
 * class TestProfileConfiguration {
 *
 *      @Provider
 *      fun newDefaultProperties(): RIdGProperties {
 *          ...
 *      }
 * }
 * ```
 * To load the above profile set the application property *ridg.profile=test*.
 * This will use the [RIdGProperties] returned from the newDefaultProperties method instead of the default object.
 *
 * This will allow you to create different versions of properties and change them quickly.
 *
 * @see Provider
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ConfigurationProfile(
    val name: String,
)
