package visitors

import tokenizer.*

class PrintVisitor: TokenVisitor {

    fun print(tokens: List<Token>) {
        for (token in tokens) {
            token.accept(this)
        }
        println()
    }

    override fun visit(token: NumberToken) {
        print("NUMBER(${token.num}) ")
    }

    override fun visit(token: LeftBrace) {
        print("LEFT ")
    }

    override fun visit(token: RightBrace) {
        print("RIGHT ")
    }

    override fun visit(token: Operation) {
        when(token.symbol) {
            "+" -> print("PLUS ")
            "-" -> print("MINUS ")
            "*" -> print("MUL ")
            "/" -> print("DIV ")
        }
    }
}