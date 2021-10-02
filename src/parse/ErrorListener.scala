package parse

import org.antlr.v4.runtime.{BaseErrorListener, RecognitionException, Recognizer}

class ErrorListener extends BaseErrorListener {

  private var errorList: List[SyntaxError] = List.empty[SyntaxError]

  def syntaxErrors(): List[SyntaxError] = errorList

  override def syntaxError(recognizer: Recognizer[_, _],
                           offendingSymbol: Object,
                           line: Int, charPositionInLine: Int,
                           msg: String, e: RecognitionException
      ): Unit = {
    errorList = errorList :+ SyntaxError(recognizer, offendingSymbol, line, charPositionInLine, msg, e)
  }

}
