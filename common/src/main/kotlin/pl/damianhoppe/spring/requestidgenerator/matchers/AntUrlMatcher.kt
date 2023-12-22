package pl.damianhoppe.spring.requestidgenerator.matchers

import org.springframework.util.AntPathMatcher

/**
 * UrlMatcher which checks the match based on the pattern
 *
 * @property pattern Url pattern
 * @see UrlMatcher
 * @see AntPathMatcher
 */
abstract class AntUrlMatcher<T>(
    private val pattern: String
): UrlMatcher<T> {

    private val matcher: AntPathMatcher = AntPathMatcher()

    /**
     * Checks the match of the url to the pattern
     *
     * @param url Url for check
     * @return true if url matches to the pattern, false if it does not
     */
    override fun matchesUrl(url: String): Boolean {
        return matcher.match(pattern, url)
    }
}