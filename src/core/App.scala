package core

import core.StateManager
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage

object App extends JFXApp {

  StateManager.updateState(State())

  def setStage(stage: PrimaryStage): Unit = {
    this.stage = stage
    stage.resizable = false
  }

  def getStage(): PrimaryStage = {
    this.stage
  }


}
