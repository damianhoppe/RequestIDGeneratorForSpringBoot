package pl.damianhoppe.spring.requestidgenerator.openfeign.interceptors

import feign.RequestTemplate
import org.junit.jupiter.api.Test
import pl.damianhoppe.spring.requestidgenerator.RequestContext
import pl.damianhoppe.spring.requestidgenerator.openfeign.interceptors.RequestIdForOpenFeignInterceptor
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RequestIdForOpenFeignInterceptorTest {

    @Test
    fun apply_ShouldAddHeaderWithRequestId_WhenIsEnabled() {
        val requestId = "adad2yubr- 3eiiJAD87"
        val requestIdHeaderName = "X-Request-Id-Header"
        RequestContext.setRequestId(requestId)

        val interceptor = RequestIdForOpenFeignInterceptor(requestIdHeaderName = requestIdHeaderName)
        val template = RequestTemplate()
        interceptor.apply(template)
        assertTrue(template.headers().containsKey(requestIdHeaderName))
        assertEquals(requestId, template.headers()[requestIdHeaderName]?.first())
    }

    @Test
    fun apply_ShouldNotAddHeaderWithRequestId_WhenIsDisabled() {
        val requestId = "adad2yubr- 3eiiJAD87"
        val requestIdHeaderName = "X-Request-Id-Header"
        RequestContext.setRequestId(requestId)

        val interceptor = RequestIdForOpenFeignInterceptor(requestIdHeaderName = requestIdHeaderName, disabled = true)
        val template = RequestTemplate()
        interceptor.apply(template)
        assertTrue(!template.headers().containsKey(requestIdHeaderName))
    }

}