package gui

import core.StateManager.{setJavaCode, translate, updateState}
import core.{App, StateManager}
import gui.UIUtilities.{openFileChooser, openOperation, saveFileChooser, showDialog}
import io.{CodeFile, IO}
import javafx.application.Platform
import javafx.scene.control.{MenuItem, TextArea}
import scalafxml.core.macros.sfxml

@sfxml
class MainSceneController(JavaTextArea: TextArea, PythonTextArea: TextArea, saveMenuItem: MenuItem) {

  // [ INITIALIZATION ]
  setSaveMenuItemStatus()
  setTitle()
  forceBinding()



  def saveOnClick(): Unit = {
    val text = Option(JavaTextArea.getText)
    val file = StateManager.getJavaCode().file
    IO.saveCodeToFile(CodeFile.withString(file, text))
  }

  def saveAsOnClick(): Unit = {
    saveFileChooser() match {
      case Some(file) =>
        val text = Option(JavaTextArea.getText)
        val code = CodeFile.withString(Some(file), text)
        updateState(setJavaCode(code))
        IO.saveCodeToFile(code)
        forceBinding()
      case None => ()
    }
  }

  def openOnClick(): Unit = {
    openFileChooser().flatMap(file => openOperation(file)) match {
      case Some(code) => updateState(setJavaCode(code))
      case None => ()
    }
    forceBinding()  // I really don't know why this is necessary, but it forces JFX to update
  }

  def closeOnClick(): Unit = {
    Platform.exit()
    System.exit(0)
  }

  // used to on initialization to set the text of the textareas as formatted based on the static representation in state
  def setFormattedText(textArea: TextArea, code: CodeFile): Unit = {
    code.raw match {
      case Some(list) =>
        textArea.clear()
        list.foreach(line => textArea.appendText(line + "\n"))
      case None => ()
    }
  }


  private def setSaveMenuItemStatus(): Unit = {
    val shouldEnable = StateManager.getJavaCode().file.isEmpty
    saveMenuItem.setDisable(shouldEnable)
  }

  private def setTitle(): Unit = {
    StateManager.getJavaCode().file match {
      case Some(file) => App.getStage().setTitle(file.getName)
      case None => ()
    }
  }

  def translateOnClick(): Unit = {
    val input = JavaTextArea.getText
    translate(input) match {
      case (s, None) => {
        updateState(s)
      }
      case (s, Some(value)) => {
        updateState(s)
        runLater(showDialog(value.message))
      }
    }
    //FIXME I cannot resolve this need to call translate twice, the JavaFX won't update properly otherwise
    translate(input) match {
      case (s, None) => {
        updateState(s)
      }
      case (s, Some(value)) => {
        updateState(s)
      }
    }
  }

  private def forceBinding(): Unit = {
    setFormattedText(JavaTextArea, StateManager.getJavaCode())
    setFormattedText(PythonTextArea, StateManager.getPythonCode())
    JavaTextArea.setText(JavaTextArea.getText)
    PythonTextArea.setText(PythonTextArea.getText)
  }

  def runLater(r: => Unit): Unit = {
    Platform.runLater(() => r)
  }

}
