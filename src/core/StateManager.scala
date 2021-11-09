package core

import io.CodeFile
import parse.{SyntaxError, TranslationUnit}
import scalafx.beans.property.StringProperty

object StateManager {

  private var currentState: State = State(CodeFile(None, None), CodeFile(None, None))
  def state(): State = currentState

  val pythonOutput = StringProperty(getPythonCodeFile().asString())


  def updateState(state: State): Unit = {
    currentState = state
    pythonOutput.update(currentState.pythonCode.asString())
  }

  // TODO: Refactor the matching logic
  def setJavaCode(code: CodeFile): State = {
    currentState match {
      case s: State =>
        s.setJavaCode(code)
      case _ => currentState
    }
  }

  def getJavaCodeFile(): CodeFile = {
    currentState match {
      case s: State => s.javaCode
      case _ => CodeFile(None, None)
    }
  }

  def getPythonCodeFile(): CodeFile = {
    currentState match {
      case s: State => s.pythonCode
      case _ => CodeFile(None, None)
    }
  }

  def translate(input: String): (State, Option[SyntaxError]) = {
    currentState match {
      case s: State  =>
        val (output, syntaxErrors) = TranslationUnit.process(input)
        val rawInput = s.javaCode.setRaw(input)
        val rawOutput = s.pythonCode.setRaw(output)
        val s2 = State(rawInput, rawOutput)
        (s2, syntaxErrors)
      case _ => (currentState, None)
    }
  }


}
