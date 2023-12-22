package pl.damianhoppe.spring.requestidgenerator.utils

import pl.damianhoppe.spring.requestidgenerator.matchers.AntUrlMatcher
import kotlin.test.*

class AntUrlMatcherTest {

    /**
     * Contains associated urlPatterns to 2 lists, first list for correct urls, second list for incorrect urls
     */
    private val tests: MutableMap<String, Pair<List<String>, List<String>>> = mutableMapOf()

    @BeforeTest
    fun prepare() {
        tests["/api/**"] = Pair(listOf(
            "/api", "/api/", "/api/0", "/api/0/", "/api/1/get",
        ), listOf(
            "", "/", "/ap", "/apid", "/apid/12",
        ))
        tests["https://second-service/api/**"] = Pair(listOf(
            "https://second-service/api", "https://second-service/api/", "https://second-service/api/0", "https://second-service/api/0/", "https://second-service/api/1/get",
        ), listOf(
            "", "/", "/ap", "/apid", "/apid/12", "/api", "/api/", "/api/0", "/api/0/", "/api/1/get",
        ))
    }

    @Test
    fun matches_ShouldReturnFalse_ForIncorrectUrls() {
        for (urlPattern in tests.keys) {
            val matcher = object: AntUrlMatcher<String>(urlPattern) {
                override fun mapToUrl(obj: String): String = obj
            }
            for(correctUrl in tests[urlPattern]!!.first) {
                assertTrue(matcher.matches(correctUrl))
            }
        }
    }

    @Test
    fun matches_ShouldReturnTrue_ForCorrectUrls() {
        for (urlPattern in tests.keys) {
            val matcher = object: AntUrlMatcher<String>(urlPattern) {
                override fun mapToUrl(obj: String): String = obj
            }
            for(correctUrl in tests[urlPattern]!!.second) {
                assertFalse(matcher.matches(correctUrl))
            }
        }
    }
}