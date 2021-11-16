package core

import core.StateManager.{getJavaCodeFile, setJavaCode, translate, updateState}
import io.IO.FileError
import io.{CodeFile, IO}
import javafx.stage.{FileChooser, Window}
import scalafx.application.Platform
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType

import java.io.File
import scala.util.{Failure, Success}

trait MainStageController {
  this: MainStage =>

  def menuItem_Save_OnAction(): Unit = {
    val text = Option(javaTextArea.getText)
    val file = StateManager.getJavaCodeFile().file
    IO.saveCodeToFile(CodeFile.withString(file, text))
  }
  def menuItem_SaveAs_OnAction(): Unit = {
    saveFileChooser() match {
      case Some(file) =>
        val text = Option(javaTextArea.getText)
        val code = CodeFile.withString(Some(file), text)
        updateState(setJavaCode(code))
        IO.saveCodeToFile(code)
      case None => ()
    }
  }

  def menuItem_Open_OnAction(): Unit = {
    for (choice <- openFileChooser()) yield openOperation(choice) match {
      case Some(code) =>
        updateState(setJavaCode(code))
        javaTextArea.setText(getJavaCodeFile().asString())
      case None => ()
    }
  }

  def menuItem_Close_OnAction(): Unit = {
    Platform.exit()
    System.exit(0)
  }

  def menuButton_Translate_OnAction(): Unit = {
    val input = javaTextArea.text.value
    translate(input) match {
      case (s, None) => updateState(s)
      case (s, Some(error)) =>
        updateState(s)
        errorAlert(error.message, "Syntax Error")
    }
  }

  private def errorAlert(message: String, header: String): Unit = {
    new Alert(AlertType.Error) {
      initOwner(App.stage)
      title = header
      headerText = header
      contentText = message
    }.showAndWait()
  }

  private def openOperation(file: File): Option[CodeFile] = {
    IO.loadFile(file) match {
      case Success(raw) => Some(CodeFile(Some(file), Some(raw)))
      case Failure(error: FileError) =>
        errorAlert(error.msg, "File Error")
        None
      case _ => None
    }
  }

  // requires the current stage/window so that it can maintain correct parent/child ownership
  def openFileChooser(window: Window = App.stage): Option[File] = {
    val fileChoose = new FileChooser()
    Option(fileChoose.showOpenDialog(window))
  }

  def saveFileChooser(window: Window = App.stage): Option[File] = {
    val fileChooser = new FileChooser()
    Option(fileChooser.showSaveDialog(window))
  }


}
