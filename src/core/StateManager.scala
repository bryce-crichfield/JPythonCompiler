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
        s.setRawInput(code)
      case _ => currentState
    }
  }

  def getJavaCode(): CodeFile = {
    currentState match {
      case s: State => s.rawInput
      case _ => CodeFile(None, None)
    }
  }

  def getPythonCode(): CodeFile = {
    currentState match {
      case s: State => s.rawOutput
      case _ => CodeFile(None, None)
    }
  }

  // ugh, wish I had a monad for this
  def translate(input: String, run: Boolean): (State, Option[SyntaxError]) = {
    currentState match {
      case s: State  =>
        val syntaxErrors = if(run) TranslationUnit.walk(input) else List.empty
        val output = TranslationUnit.show()
        val rawInput = s.rawInput.setRaw(input)
        val rawOutput = s.rawOutput.setRaw(output)
        val s2 = State(rawInput, rawOutput)
        (s2, syntaxErrors.headOption)
      case _ => (currentState, None)
    }
  }


}
