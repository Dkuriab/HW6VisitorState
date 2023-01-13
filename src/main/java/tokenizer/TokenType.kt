package tokenizer

import visitors.TokenVisitor

interface Token {
    fun accept(visitor: TokenVisitor)
}

data class NumberToken(
    val num: Int
): Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

object LeftBrace: Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

object RightBrace: Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }
}

data class Operation(
    val function: (Int, Int) -> Int,
    val symbol: String,
): Token {
    override fun accept(visitor: TokenVisitor) {
        visitor.visit(this)
    }

    fun apply(left: NumberToken, right: NumberToken) =
        NumberToken(
            function.invoke(left.num, right.num)
        )
}