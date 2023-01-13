import tokenizer.Tokenizer
import visitors.PrintVisitor

fun main(vararg args: String) {
    val printVisitor = PrintVisitor()
    val tokenizer = Tokenizer(readLine()!!)
    val tokens = tokenizer.tokenize()

    for (token in tokens) {
        token.accept(printVisitor)
    }
}