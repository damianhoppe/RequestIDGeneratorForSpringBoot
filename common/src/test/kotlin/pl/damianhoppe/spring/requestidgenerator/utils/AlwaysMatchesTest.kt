package pl.damianhoppe.spring.requestidgenerator.utils

import pl.damianhoppe.spring.requestidgenerator.matchers.AlwaysMatches
import java.util.concurrent.ThreadLocalRandom
import kotlin.random.Random
import kotlin.streams.asSequence
import kotlin.test.*

class AlwaysMatchesTest {

    private lateinit var matcher: AlwaysMatches<String>

    @BeforeTest
    fun prepare() {
        matcher = AlwaysMatches()
    }

    @Test
    fun matches_ShouldReturnTrue_For100RandomUrls() {
        for (i in 0..100) {
            val randomUrl = nextRandomString(5, 80)
            assertTrue { matcher.matches(randomUrl) }
        }
    }


    private val charPool : List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9') + '/'

    private fun nextRandomString(length: Long) =
        nextRandomString { length }

    private fun nextRandomString(randomLengthFrom: Long, randomLengthUntil: Long) =
        nextRandomString { Random.nextLong(randomLengthFrom, randomLengthUntil) }

    private fun nextRandomString(length: () -> Long) = ThreadLocalRandom.current()
        .ints(length.invoke(), 0, charPool.size)
        .asSequence()
        .map(charPool::get)
        .joinToString("")
}