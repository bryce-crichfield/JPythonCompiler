package core

import core.state.{MainState, StartState, StateManager}
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage

object App extends JFXApp {

  StateManager.transition(MainState())

  def setStage(stage: PrimaryStage): Unit = {
    this.stage = stage
    stage.resizable = false
  }

  def getStage(): PrimaryStage = {
    this.stage
  }


}
