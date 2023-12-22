package pl.damianhoppe.spring.requestidgenerator.matchers

import pl.damianhoppe.spring.requestidgenerator.interfaces.Matcher

/**
 * Matcher which always returns a match
 * @see Matcher
 */
class AlwaysMatches<T>: Matcher<T> {

    /**
     * Always returns a match
     *
     * @return true
     */
    override fun matches(forCheck: T): Boolean {
        return true
    }
}