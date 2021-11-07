name := "CrossCompiler"

version := "0.1"

scalaVersion := "2.13.6"

// compilation flags
scalacOptions ++= Seq("-unchecked", "-deprecation", "-Xcheckinit", "-encoding", "utf8", "-Ymacro-annotations")

// libraries
val scalaFX =   "org.scalafx" %% "scalafx" % "16.0.0-R22"
val antlr =     "org.antlr" % "antlr4-runtime" % "4.7.2"


resourceDirectory in Compile := (scalaSource in Compile).value
libraryDependencies ++= Seq(scalaFX, antlr)

// Add OS specific JavaFX dependencies
val javafxModules = Seq("base", "controls", "fxml", "graphics", "media", "swing", "web")
val osName = System.getProperty("os.name") match {
  case n if n.startsWith("Linux") => "linux"
  case n if n.startsWith("Mac") => "mac"
  case n if n.startsWith("Windows") => "win"
  case _ => throw new Exception("Unknown platform!")
}
libraryDependencies ++= javafxModules.map(m => "org.openjfx" % s"javafx-$m" % "16" classifier osName)