package visitors

import errors.InconsistentBracesException
import tokenizer.*
import java.lang.IllegalArgumentException

class ParserVisitor: TokenVisitor {
    private var stack: MutableList<Token> = mutableListOf()
    private var resultList: MutableList<Token> = mutableListOf()

    fun parse(tokens: List<Token>): List<Token> {
        for (token in tokens) {
            token.accept(this)
        }
        return finish()
    }

    override fun visit(token: LeftBrace) {
        stack.add(token)
    }

    override fun visit(token: RightBrace) {
        while (stack.isNotEmpty() && stack.last() != LeftBrace) {
            resultList.add(stack.removeLast())
        }
        if (stack.isEmpty()) {
            throw InconsistentBracesException
        }
        stack.removeLast()
    }

    override fun visit(token: NumberToken) {
        resultList.add(token)
    }

    override fun visit(token: Operation) {
        stack.add(token)
    }

    fun finish(): List<Token> {
        while (stack.isNotEmpty()) {
            resultList.add(stack.removeLast())
        }
        stack = mutableListOf()

        return resultList.also {
            resultList = mutableListOf()
        }
    }
}