import tokenizer.Tokenizer
import visitors.CalcVisitor
import visitors.ParserVisitor
import visitors.PrintVisitor
import kotlin.jvm.Throws

fun main() {
    while (true) {
        val input = readLine()!!
        println(calcExpression(input))
    }
}

@Throws(IllegalArgumentException::class)
fun calcExpression(expression: String): Int {
    val printVisitor = PrintVisitor()
    val parserVisitor = ParserVisitor()
    val calcVisitor = CalcVisitor()

    val tokenizer = Tokenizer(expression)
    val tokens = tokenizer.tokenize()

    val polishNotationTokens = parserVisitor.parse(tokens)
    printVisitor.print(polishNotationTokens)

    return calcVisitor.calc(polishNotationTokens)
}