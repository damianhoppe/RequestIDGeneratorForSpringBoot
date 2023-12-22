package pl.damianhoppe.spring.requestidgenerator

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import kotlin.test.BeforeTest

class RequestContextTest {

    @BeforeTest
    fun prepare() {
        RequestContext.clear()
    }

    @Test
    fun put_ShouldPutsValueUnderKey_And_get_ShouldReturnPuttedValue() {
        val key = "key0"
        var value = "value0"
        RequestContext.put(key, value)
        assertEquals(value, RequestContext.get(key))
        value = "value1"
        RequestContext.put(key, value)
        assertEquals(value, RequestContext.get(key))
    }

    @Test
    fun remove_ShouldRemoveValueUnderKey() {
        val key = "key0"
        val value = "value0"
        RequestContext.put(key, value)
        assertEquals(value, RequestContext.get(key))
        RequestContext.remove(key)
        assertEquals(null, RequestContext.get(key))
    }

    @Test
    fun clear_ShouldRemoveAllValues() {
        val key = "key0"
        val value = "value0"
        RequestContext.put(key, value)
        assertEquals(value, RequestContext.get(key))
        RequestContext.clear()
        assertEquals(null, RequestContext.get(key))
    }

    @Test
    fun setRequestId_ShouldSetRequestId_And_getRequestId_ShouldReturnRequestId() {
        val requestId = "adpoq7r8ADS-adseeq"
        RequestContext.setRequestId(requestId)
        assertEquals(requestId, RequestContext.requestId)
    }

    @Test
    fun clearRequestId_ShouldRemoveRequestId() {
        val requestId = "adpoq7r8ADS-adseeq"
        RequestContext.setRequestId(requestId)
        assertEquals(requestId, RequestContext.requestId)
        RequestContext.clearRequestId()
        assertEquals(null, RequestContext.requestId)
    }

    @Test
    fun clear_ShouldRemoveRequestId() {
        val requestId = "adpoq7r8ADS-adseeq"
        RequestContext.setRequestId(requestId)
        assertEquals(requestId, RequestContext.requestId)
        RequestContext.clear()
        assertEquals(null, RequestContext.requestId)
    }
}