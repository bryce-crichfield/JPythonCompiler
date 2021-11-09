package core

import scalafx.application.JFXApp
import scalafx.beans.property.StringProperty
import scalafx.scene.Scene
import scalafx.scene.control.{MenuButton, MenuItem, SeparatorMenuItem, TextArea}
import scalafx.scene.layout.{AnchorPane, HBox, VBox}

class MainStage extends JFXApp.PrimaryStage with MainStageController {

  val javaCode = new StringProperty("")


  val MenuItem_Save = new MenuItem {
    mnemonicParsing = false
    text = "Save"
    onAction = _ => menuItem_Save_OnAction()
  }

  val MenuItem_SaveAs = new MenuItem {
    mnemonicParsing = false
    text = "Save As"
    onAction = _ => menuItem_SaveAs_OnAction()
  }

  val MenuItem_Open = new MenuItem {
    mnemonicParsing = false
    text = "Open"
    onAction = _ => menuItem_Open_OnAction()
  }

  val MenuItem_Close = new MenuItem {
    mnemonicParsing = false
    text = "Close"
    onAction = _ => menuItem_Close_OnAction()
  }

  val MenuButton_Translate = new MenuItem() {
    mnemonicParsing = false
    text = "Translate"
//    style <== when(hover) choose "-fx-background-color: green" otherwise "-fx-background-color: yellow"
    onAction = _ => menuButton_Translate_OnAction()
  }

  val javaTextArea: TextArea = new TextArea {
    prefWidth = 690; prefHeight = 200;
    style = "-fx-font-family: monospace"
  }

  val pythonTextArea: TextArea = new TextArea {
    prefWidth = 690; prefHeight = 200;
    style = "-fx-font-family: monospace"
    editable = false
    text <== StateManager.pythonOutput
  }

  val menu = List (
    new MenuButton {
      prefWidth = 65; prefHeight = 30
      text = "File"
      items = Seq (
      MenuItem_Save,
      MenuItem_SaveAs,
      MenuItem_Open,
        MenuButton_Translate,
      new SeparatorMenuItem(),
      MenuItem_Close )
    }
  )

  val AnchorPane_PrimaryScene: AnchorPane = new AnchorPane {
    prefWidth = 1280; prefHeight = 720
    children = new VBox {
      prefWidth = 1280; prefHeight = 720
      children = Seq (
        new HBox {
          minHeight = 30; maxHeight = 30; prefHeight = 30;
          prefWidth = 1280;
          children = menu
        },
        new HBox {
          prefWidth = 1280; prefHeight = 690;
          children = Seq(javaTextArea, pythonTextArea)
        }
      )
    }
  }

  title.value = "Cross-Compiler"
  width = 1280
  height = 720
  scene = new Scene {
    content = AnchorPane_PrimaryScene
  }

}
