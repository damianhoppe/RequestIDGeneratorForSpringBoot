package pl.damianhoppe.spring.requestidgenerator.matchers

import pl.damianhoppe.spring.requestidgenerator.interfaces.Matcher

/**
 * Url match checking interface
 */
@FunctionalInterface
interface UrlMatcher<T> : Matcher<T> {

    override fun matches(forCheck: T): Boolean {
        return matchesUrl(mapToUrl(forCheck))
    }

    /**
     * Checks url matching
     *
     * @param url Url for check
     * @return true if the url matches, false if it does not
     */
    fun matchesUrl(url: String): Boolean

    /**
     * Maps object to url
     *
     * @param obj Object to map
     * @return The mapped url based on the input object
     */
    fun mapToUrl(obj: T): String
}