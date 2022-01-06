package hr.flowable.composeplayground

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun findClosestValue(){
        val testData = mapOf(
            0 to -254,
            1 to -6,
            2 to 258
        )

        val valueToCheck = 250

        val closestItem = testData.map { it.key to it.value - valueToCheck }.minByOrNull { it.second }

        assertEquals(2,closestItem!!.first)

    }
}
