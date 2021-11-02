package core

import core.UIUtilities.primaryStageBuilder
import io.ResourcePaths.MainScene
import io.CodeFile
import scalafx.application.JFXApp.PrimaryStage

case class State(javaCode: CodeFile = CodeFile(None, None),
                 pythonCode: CodeFile = CodeFile(None, None)) {
  // This should probably be moved, but for now it can live here
  def setJavaCode(input: CodeFile): State = this.copy(javaCode = input)

  def setPythonCode(output: CodeFile): State = this.copy(pythonCode = output)



//  val stage: PrimaryStage = createStage(MainScene)(primaryStageBuilder)
}
