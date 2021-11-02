package core

import io.IO.FileError
import io.{CodeFile, IO}
import javafx.scene.Scene
import javafx.scene.control.{ButtonType, Dialog}
import javafx.stage.{FileChooser, Modality, Stage, Window}
import scalafx.application.JFXApp.PrimaryStage

import java.io.File
import scala.util.{Failure, Success}

object UIUtilities {


//  // Given a title of an FXML, will attempt to load the corresponding Scene from disk
//  def loadScene(title: String): Try[Scene] = {
//    loadParent(title).flatMap(parent => Try(new Scene(parent)))
//  }

//  // Given a title of an FXML, will attempt to load the FXML as a parent from disk
//  def loadParent(title: String): Try[Parent] = {
//    for (url <- IO.loadFXML(title)) yield FXMLView(url, NoDependencyResolver)
//  }


//  /**
//   *
//   * @param sceneTitle represents the file name of a particular FXMl scene that will be loaded
//   * @param builder    builds a stage of type S from the scene that is loaded via the sceneTitle
//   * @tparam S a particular type of Stage
//   * @return
//   */
//  def createStage[S](sceneTitle: String)(builder: Scene => S): S = {
//    loadScene(sceneTitle) match {
//      case Success(scene: Scene) => builder(scene)
//      case Failure(exception) =>
//        println(s"Stage Creation Failed! Attempting to Recover with Null Scene! \n${exception.toString}")
//        builder(null)
//    }
//  }

  val primaryStageBuilder: Scene => PrimaryStage = (s: Scene) => new PrimaryStage {
    scene = new scalafx.scene.Scene(s)
  }
  val normalStageBuilder: Scene => Stage = (s: Scene) => new Stage {
    setScene(s)
  }
  //Unused for now, but can be used to load popups
  val dialogStageBuilder: Scene => Stage = (s: Scene) => new Stage {
    setTitle("Load File")
    setScene(s)
    initModality(Modality.APPLICATION_MODAL)
  }

  // requires the current stage/window so that it can maintain correct parent/child ownership
  def openFileChooser(window: Window): Option[File] = {
    val fileChoose = new FileChooser()
    Option(fileChoose.showOpenDialog(window))
  }

  def saveFileChooser(window: Window): Option[File] = {
    val fileChooser = new FileChooser()
    Option(fileChooser.showSaveDialog(window))
  }

  /** will attempt to write a file from storage, then if successful, will return a codeFile representation
   * guarantees a "full" codeFile from the chosen file or else nothing */
  def openOperation(file: File): Option[CodeFile] = {
    IO.loadFile(file) match {
      case Success(raw) => Some(CodeFile(Some(file), Some(raw)))
      case Failure(error: FileError) =>
        showDialog(error.msg)
        None
      case _ => None
    }
  }

  def showDialog(msg: String): Unit = {
    val dialog = new Dialog();
    dialog.getDialogPane.setContentText(msg)
    dialog.getDialogPane.getButtonTypes.add(ButtonType.OK)
    dialog.showAndWait()
  }


}
