package pl.damianhoppe.spring.requestidgenerator.config

/**
 * Functional interface to customize object
 *
 * @param T The type of object to customize
 */
@FunctionalInterface
fun interface Customizer<T> {

    /**
     * Customize input object
     *
     * @param toCustomize Input object to customize
     */
    fun customize(toCustomize: T)
}