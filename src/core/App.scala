package core

import scalafx.application.JFXApp

object App extends JFXApp {

//  StateManager.updateState(State())
  val mainStage = new MainStage
  this.stage = mainStage
//  this.stage.resizable = false


}
