package core.state
import global.ResourcePaths.MainScene
import gui.UIUtilities._
import io.{CodeFile, IO}
import org.antlr.v4.runtime.tree.ParseTreeWalker
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import parse.antlr.{Java8Lexer, Java8Parser}
import parse.{ParserListener, TranslationUnit}
import scalafx.application.JFXApp.PrimaryStage

import java.io.ByteArrayInputStream

case class MainState(rawInput: CodeFile = CodeFile(None, None),
                     rawOutput: CodeFile = CodeFile(None, None)) extends State {
  // This should probably be moved, but for now it can live here
  def setRawInput(input: CodeFile): MainState = this.copy(rawInput = input)
  def setRawOutput(output: CodeFile): MainState = this.copy(rawOutput = output)
  override val stage: PrimaryStage = createStage(MainScene)(primaryStageBuilder)
}

