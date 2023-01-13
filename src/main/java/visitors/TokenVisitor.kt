package visitors

import tokenizer.*

interface TokenVisitor {
    fun visit(token: NumberToken)
    fun visit(token: LeftBrace)
    fun visit(token: RightBrace)
    fun visit(token: Operation)
}