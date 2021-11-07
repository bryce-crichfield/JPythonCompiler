package core

import scalafx.application.JFXApp

object App extends JFXApp {
  val mainStage = new MainStage
  this.stage = mainStage
}
