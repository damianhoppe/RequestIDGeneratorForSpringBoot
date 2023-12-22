package pl.damianhoppe.spring.requestidgenerator.core.matchers

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockHttpServletRequest

class HttpServletRequestAntUrlMatcherTest {

    @Test
    fun mapToUrl_ShouldReturnRequestURIFromHttpServletRequest() {
        val request = MockHttpServletRequest()
        request.requestURI = "/get/request/uri"
        val matcher = HttpServletRequestAntUrlMatcher("/get")
        Assertions.assertEquals(request.requestURI, matcher.mapToUrl(request))
    }
}