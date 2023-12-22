package pl.damianhoppe.spring.requestidgenerator.logback.interceptors

import org.junit.jupiter.api.Test
import org.slf4j.MDC
import org.springframework.mock.web.MockHttpServletRequest
import kotlin.test.assertEquals

class MDCRequestIdInterceptorTest {

    @Test
    fun requestIdAssigned_ShouldSetRequestIdInMDC_ForMdcKeyGiven() {
        val mdcKey = "RequestIdKey"
        val requestId = "kaWe8a-3wriDAfe"
        val request = MockHttpServletRequest()
        val interceptor = MDCRequestIdInterceptor(mdcKey)
        interceptor.requestIdAssigned(requestId, request)
        assertEquals(requestId, MDC.get(mdcKey))
    }

    @Test
    fun requestIdAssigned_ShouldRemoveRequestIdFromMDC_ForMdcKeyGiven() {
        val mdcKey = "RequestIdKey"
        val requestId = "kaWe8a-3wriDAfe"
        val request = MockHttpServletRequest()
        val interceptor = MDCRequestIdInterceptor(mdcKey)
        interceptor.requestIdAssigned(requestId, request)
        assertEquals(requestId, MDC.get(mdcKey))
        interceptor.requestFinished(requestId, request)
        assertEquals(null, MDC.get(mdcKey))
    }
}