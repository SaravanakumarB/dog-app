import org.junit.Assert
import org.junit.Test

class Calculator {
    fun add(a: Int, b: Int): Int {
        return a + b
    }
}
class Sample {
    @Test
    fun testAdd() {
        val calculator = Calculator()
        val result = calculator.add(2, 3)
        Assert.assertEquals(5, result)
    }
}