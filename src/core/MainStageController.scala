package core

import core.StateManager.{translate, updateState}
import core.UIUtilities.showDialog
import io.CodeFile
import scalafx.application.Platform
import scalafx.application.Platform.runLater
import scalafx.scene.input.KeyEvent

trait MainStageController {
  this: MainStage =>

  def menuItem_Save_OnAction(): Unit = ???
  def menuItem_SaveAs_OnAction(): Unit = ???
  def menuItem_Open_OnAction(): Unit = ???

  def menuItem_Close_OnAction(): Unit = {
    Platform.exit()
    System.exit(0)
  }

  def menuButton_Translate_OnAction(): Unit = {
    val input = javaTextArea.text.value
    println(input)
    translate(input) match {
      case (s, None) => {
        updateState(s)
      }
      case (s, Some(value)) => {
        updateState(s)
//        runLater(showDialog(value.message))
      }
    }
  }

  var lastKeyPressTime: Long = 0
  var lastTranslateTime: Long = 0
  def textArea_Java_OnKeyPressed(e: KeyEvent): Unit = {
    val current = System.currentTimeMillis()
//    val timeSincePress = (current - lastKeyPressTime) > 1000
    val timeSincePress = true
    val timeSinceTrans = (current - lastTranslateTime) > 4000
    if(timeSincePress && timeSinceTrans) {
      val exclude = List(' ', '\n', '\t')
      if(!exclude.contains(e.character)) {
        println("Translating")
        menuButton_Translate_OnAction()
        lastTranslateTime = System.currentTimeMillis()
      }
    }
    lastKeyPressTime = System.currentTimeMillis()
  }

}
