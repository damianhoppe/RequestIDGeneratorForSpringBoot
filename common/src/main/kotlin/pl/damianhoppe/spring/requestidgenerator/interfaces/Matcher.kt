package pl.damianhoppe.spring.requestidgenerator.interfaces

/**
 * Object matcher
 * @param T The type of the object being checked
 */
@FunctionalInterface
fun interface Matcher<T> {

    /**
     * Checks object matching
     *
     * @param forCheck Object for check
     * @return true if the object matches, false if it does not
     */
    fun matches(forCheck: T): Boolean
}