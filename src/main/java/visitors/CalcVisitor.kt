package visitors

import errors.InconsistentBracesException
import errors.NotEnoughOperandsException
import tokenizer.*

class CalcVisitor: TokenVisitor {
    private val stack = mutableListOf<Token>()

    fun calc(tokens: List<Token>): Int {
        for (token in tokens) {
            token.accept(this)
        }
        return (stack[0] as NumberToken).num
    }

    override fun visit(token: NumberToken) {
        stack.add(token)
    }

    override fun visit(token: LeftBrace) {
        throw InconsistentBracesException
    }

    override fun visit(token: RightBrace) {
        throw InconsistentBracesException
    }

    override fun visit(token: Operation) {
        val rightOperand = stack.removeLastOrNull()
        val leftOperand = stack.removeLastOrNull()

        if (rightOperand !is NumberToken || leftOperand !is NumberToken) {
            throw NotEnoughOperandsException
        }

        stack.add(token.apply(leftOperand, rightOperand))
    }

}