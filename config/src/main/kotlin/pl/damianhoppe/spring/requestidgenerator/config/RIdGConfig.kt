package pl.damianhoppe.spring.requestidgenerator.config

/**
 * Stores sub configs for request id generator and all its submodules.
 * This class is only used to read configs, to edit config see [RIdGConfigurer]
 *
 * @property configs Map of sub configs, the keys are the classes of these configs
 * and the values are the object instances of these classes
 * @see RIdGConfigurer
 */
class RIdGConfig(
    val configs: Map<Class<out RIdGSubConfig>, RIdGSubConfig> = emptyMap()
) {

    /**
     * Gets an instance of sub config object
     *
     * @param T The type of sub config class to get
     * @return Object instance of sub config class
     * @throws IllegalStateException If sub config class not found in configs
     */
    inline fun <reified T: RIdGSubConfig> getConfig(): T {
        return (configs[T::class.java]?: throw IllegalArgumentException("Config ${T::class.java.simpleName} not found")) as T
    }

    /**
     * It should not be used in production as all configuration classes should be loaded automatically!
     * Gets an instance of sub config object or null if not found
     *
     * @param T The type of sub config class to get
     * @return Object instance of sub config class or null if not found
     */
    inline fun <reified T: RIdGSubConfig> getConfigOrNull(): T? {
        return (configs[T::class.java] ?: return null) as T
    }
}