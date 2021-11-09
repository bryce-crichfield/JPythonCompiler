package io

import java.io.File

case class CodeFile(file: Option[File], raw: Option[List[String]]) {
  def setLines(lines: Option[List[String]]): CodeFile = {
    this.copy(raw = lines)
  }

  def asString(): String = {
    raw match {
      case Some(lines) =>
        lines.foldLeft("")((sum, l) => sum + l + "\n")
      case None => ""
    }
  }

  def setRaw(str: String): CodeFile = {
    val lines = str.split("\n").toList
    this.copy(raw = Option(lines))
  }
}
object CodeFile {

  //constructs a new CodeFile from a contiguous string rather than from a list of strings (line separated)
  def withString(file: Option[File], asString: Option[String]): CodeFile = {
    val lines = asString.map(string => string.split("\n").toList)
    CodeFile(file, lines)
  }
}
