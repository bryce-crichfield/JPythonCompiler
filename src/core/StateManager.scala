package core

import io.CodeFile
import parse.{SyntaxError, TranslationUnit}

object StateManager {

  private var currentState: State = _

  def state(): State = currentState


  def updateState(state: State): Unit = {
    currentState = state
    App.setStage(state.stage)
  }

  // TODO: Refactor the matching logic
  def setJavaCode(code: CodeFile): State = {
    currentState match {
      case s: State =>
        s.setJavaCode(code)
      case _ => currentState
    }
  }

  def getJavaCode(): CodeFile = {
    currentState match {
      case s: State => s.javaCode
      case _ => CodeFile(None, None)
    }
  }

  def getPythonCode(): CodeFile = {
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
