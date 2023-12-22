package pl.damianhoppe.spring.requestidgenerator.config

/**
 * Stores default sub configs for request id generator and all its submodules.
 * Allow to customize default configuration.
 *
 * @property configs Map of sub configs, the keys are the classes of these configs
 * and the values are the object instances of these classes
 */
class RIdGConfigurer(
    val configs: Map<Class<out RIdGSubConfig>, RIdGSubConfig> = emptyMap()
) {

    /**
     * Customize specific sub config
     *
     * @param T The type of sub config class to customize
     * @return [RIdGConfigurer] to further customizations
     * @throws IllegalStateException If sub config class not found in configs
     */
    inline fun <reified T: RIdGSubConfig> customize(customizer: Customizer<T>): RIdGConfigurer {
        customizer.customize((configs[T::class.java]?: throw IllegalArgumentException("Config ${T::class.java.simpleName} not found")) as T)
        return this
    }

    /**
     * It should not be used in production as all configuration classes should be loaded automatically!
     * Customize specific sub config.
     * A safer version of [customize] method, it does not throw exception when specific
     * sub config not found.
     *
     * @param T The type of sub config class to customize
     * @return [RIdGConfigurer] to further customizations
     */
    inline fun <reified T: RIdGSubConfig> tryCustomize(customizer: Customizer<T>): RIdGConfigurer {
        customizer.customize((configs[T::class.java]?: return this) as T)
        return this
    }

    /**
     * Creates a config instance
     *
     * @return Built config instance
     * @throws Exception When verifying the correctness of the sub configs
     */
    fun build(): RIdGConfig {
        configs.values.forEach {it
            .verifyCorrectness()
        }
        return RIdGConfig(configs)
    }
}