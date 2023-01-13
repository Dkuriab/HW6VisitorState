package errors

sealed class ParserException(message: String): IllegalArgumentException(message)

object NotEnoughOperandsException: ParserException("Not enough operands")

object InconsistentBracesException: ParserException("Inconsistent braces")

class IncorrectSymbolException(position: Int): ParserException("Incorrect symbol at position: $position")
