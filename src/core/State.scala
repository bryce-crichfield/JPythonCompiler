package core

import io.ResourcePaths.MainScene
import gui.UIUtilities.{createStage, primaryStageBuilder}
import io.CodeFile
import scalafx.application.JFXApp.PrimaryStage

case class State(rawInput: CodeFile = CodeFile(None, None),
                 rawOutput: CodeFile = CodeFile(None, None)) {
  // This should probably be moved, but for now it can live here
  def setRawInput(input: CodeFile): State = this.copy(rawInput = input)

  def setRawOutput(output: CodeFile): State = this.copy(rawOutput = output)

  val stage: PrimaryStage = createStage(MainScene)(primaryStageBuilder)
}
