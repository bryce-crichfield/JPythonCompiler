package io

import java.io.{BufferedWriter, File, FileWriter}
import java.net.URL
import scala.io.{BufferedSource, Source}
import scala.util.{Failure, Success, Try}


object IO {

  case class FileError(msg: String = "") extends Throwable {
    override def toString: String = s"IO.FileError\n$msg"
  }
  object FileError {
    def asFailure[A](msg: String): Failure[A] = Failure(FileError(msg))
  }
  private def verifyFileType(file: File): Try[File] = {
    if(file.getName.takeRight(4).equals("java")) Success(file)
    else FileError.asFailure("Wrong File Extension - Please Only Choose a .java File!")
  }
  private def loadSource(file: File): Try[BufferedSource] = Try(Source.fromFile(file))
  private def readSource(source: BufferedSource): Try[List[String]] = Try {
    val raw = source.getLines().toList
    source.close()
    raw
  }

  def loadFile(file: File): Try[List[String]] = {
    for {
      verify <- verifyFileType(file)
      load <- loadSource(verify)
      read <- readSource(load)
    } yield read
  }

  // the way this currently works, is it will simply write a new file then
  // overwrite any preexisting file with the same namespace
  def saveCodeToFile(code: CodeFile): Try[Unit] = {
    if(code.file.isEmpty) FileError.asFailure("No File Defined")
    else if(code.raw.isEmpty) FileError.asFailure("No Lines Defined")
    else {
      val newFile = new File(code.file.get.getAbsolutePath)
      val writer = new BufferedWriter(new FileWriter(newFile))
      Try {
        code.raw.get.foreach(line => writer.write(line + "\n"))
        writer.close()
      }
    }
  }

}
