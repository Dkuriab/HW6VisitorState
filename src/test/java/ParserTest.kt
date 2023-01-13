import errors.InconsistentBracesException
import errors.IncorrectSymbolException
import errors.NotEnoughOperandsException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class ParserTest {
    @Test
    fun validExpressions() {
        assertEquals(4, calcExpression("(30 + 2) / 8"))
        assertEquals(100, calcExpression("(125 * 4) / (2 + 3)"))
    }

    @Test
    fun correctWhitespacesParsing() {
        assertEquals(4, calcExpression("  (    30  \t  +  2)     /   \n    8 "))
    }

    @Test
    fun incorrectOperandsCountException() {
        assertThrows(NotEnoughOperandsException::class.java) { calcExpression("2 +") }
    }

    @Test
    fun incorrectSymbolException() {
        assertThrows(IncorrectSymbolException::class.java) { calcExpression("123 ? 123") }
    }

    @Test
    fun inconsistentBracesException() {
        assertThrows(InconsistentBracesException::class.java) { calcExpression("(123 + 123") }
        assertThrows(InconsistentBracesException::class.java) { calcExpression("123 + 123)") }
    }
}