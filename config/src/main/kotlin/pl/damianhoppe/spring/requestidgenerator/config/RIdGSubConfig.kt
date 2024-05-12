package pl.damianhoppe.spring.requestidgenerator.config

/**
 * Interface to identify sub config class.
 * Allows to load all classes implementing this interface
 * using *ApplicationContext.getBeansOfType*.
 *
 * @see org.springframework.context.ApplicationContext.getBeansOfType
 */
interface RIdGSubConfig {

    /**
     * Verify correction of config values
     * @throws IllegalStateException For invalid values
     */
    fun verifyCorrectness() {}
}