package visitors

import tokenizer.*

class PrintVisitor: TokenVisitor {
    override fun visit(token: StartToken) {}

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

    override fun visit(token: EndToken) {}
}