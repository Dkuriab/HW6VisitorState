import tokenizer.Tokenizer
import visitors.ParserVisitor
import visitors.PrintVisitor

fun main() {
    while (true) {
        val input = readLine()!!
        val printVisitor = PrintVisitor()
        val parserVisitor = ParserVisitor()
        val tokenizer = Tokenizer(input)
        val tokens = tokenizer.tokenize()

        for (token in tokens) {
            token.accept(printVisitor)
            token.accept(parserVisitor)
        }

        println()

        val polishNotationTokens = parserVisitor.finish()

        for (token in polishNotationTokens) {
            token.accept(printVisitor)
        }

        println()

    }

}