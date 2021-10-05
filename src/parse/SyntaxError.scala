package parse

import org.antlr.v4.runtime.{RecognitionException, Recognizer}

case class SyntaxError(
        recognizer: Recognizer[_,_],
        offendingSymbol: Object,
        line: Int,
        index: Int,
        message: String,
        error: RecognitionException
) {
  override def toString: String = {
    s"$message at line ($line, $index)"
  }
}
