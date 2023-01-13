package tokenizer

import java.lang.Character.isWhitespace
import java.util.Collections.emptyList

enum class AutomatonPosition {
    Start,
    Number,
    Error,
    End,
}

data class TokenizerState(
    val automatonPosition: AutomatonPosition,
    val position: Int,
    val tokens: List<Token>,
    val tokenSequence: String,
) {
    companion object {
        fun defaultState() = TokenizerState(
            automatonPosition = AutomatonPosition.Start,
            position = 0,
            tokens = emptyList(),
            tokenSequence = "",
        )
    }
}

class Tokenizer(val input: String) {
    var state: TokenizerState = TokenizerState.defaultState()

    fun tokenize(): List<Token> {

        while (state.position < input.length && state.automatonPosition !in setOf(
                AutomatonPosition.End,
                AutomatonPosition.Error
            )
        ) {
            skipWhitespaces()
            parseNext()
        }
        flush()

        if (state.automatonPosition == AutomatonPosition.Error) {
            throw IllegalArgumentException("Incorrect symbol at position: ${state.position}")
        }

        return state.tokens
    }

    private fun flush() {
        if (state.tokenSequence.isNotEmpty() && state.automatonPosition == AutomatonPosition.Number) {
            state = state.copy(
                tokenSequence = "",
                tokens = state.tokens + NumberToken(Integer.parseInt(state.tokenSequence))
            )
        }
    }

    private fun parseNext() {
        when (state.automatonPosition) {
            AutomatonPosition.Start -> when (input[state.position]) {
                '(' -> {
                    state = state.copy(
                        position = state.position + 1,
                        tokens = state.tokens + LeftBrace
                    )
                }
                ')' -> {
                    state = state.copy(
                        position = state.position + 1,
                        tokens = state.tokens + RightBrace
                    )
                }
                '*' -> {
                    state = state.copy(
                        position = state.position + 1,
                        tokens = state.tokens + Operation({ a, b -> a * b }, "*")
                    )
                }
                '+' -> {
                    state = state.copy(
                        position = state.position + 1,
                        tokens = state.tokens + Operation({ a, b -> a + b }, "+")
                    )
                }
                '-' -> {
                    state = state.copy(
                        position = state.position + 1,
                        tokens = state.tokens + Operation({ a, b -> a - b }, "-")
                    )
                }
                '/' -> {
                    state = state.copy(
                        position = state.position + 1,
                        tokens = state.tokens + Operation({ a, b -> a / b }, "/")
                    )
                }
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                    state = state.copy(
                        automatonPosition = AutomatonPosition.Number
                    )
                }
                else -> {
                    state = state.copy(
                        automatonPosition = AutomatonPosition.Error
                    )
                }
            }
            AutomatonPosition.Number -> {
                when (input[state.position]) {
                    '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                        state = state.copy(
                            position = state.position + 1,
                            tokenSequence = state.tokenSequence + input[state.position]
                        )
                    }
                    else -> {
                        state = state.copy(
                            tokenSequence = "",
                            automatonPosition = AutomatonPosition.Start,
                            tokens = state.tokens + NumberToken(Integer.parseInt(state.tokenSequence))
                        )
                    }
                }
            }
            AutomatonPosition.Error -> return
            AutomatonPosition.End -> {
                state = state.copy(
                    tokenSequence = "",
                    tokens = state.tokens + NumberToken(Integer.parseInt(state.tokenSequence))
                )
            }
        }
    }

    private fun skipWhitespaces() {
        var firstNonEmptyIndex = state.position
        while (state.position < input.length && isWhitespace(input[firstNonEmptyIndex])) {
            firstNonEmptyIndex++;
        }

        state = state.copy(
            position = firstNonEmptyIndex
        )
    }
}