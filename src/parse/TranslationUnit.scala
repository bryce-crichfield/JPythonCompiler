package parse

import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream
import org.antlr.v4.runtime.tree.ParseTreeWalker
import parse.antlr.{Java8Lexer, Java8Parser}

import java.io.ByteArrayInputStream
import java.io.InputStream
import java.nio.charset.StandardCharsets

// acts as a container for the ANTLR logics
// this is likely very inefficient but it works for now
object TranslationUnit {

  private var currentScope: Int = 0
  private val internalBuilder: StringBuilder = new StringBuilder()

  def show(): String = internalBuilder.mkString

  private def stringToCharStream(str: String): CharStream = {
    val stream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8))
    CharStreams.fromStream(stream)
  }

  // this is probably inefficient, will be forced to rebuild AST each time,
  // works for now via a translate button, but will need to be more resilient once real-time updates are implemented
  def walk(input: String): List[SyntaxError] = {
    internalBuilder.clear()
    val lexer = new Java8Lexer(stringToCharStream(input))
    val tokens = new CommonTokenStream(lexer)
    val parser = new Java8Parser(tokens)
    val errorListener = new ErrorListener
    parser.addErrorListener(errorListener)
    val tree = parser.compilationUnit()
    val walker = new ParseTreeWalker()
    val parserListener = new ParserListener(parser)
    walker.walk(parserListener, tree)
    errorListener.syntaxErrors()
  }

  def enterScope(): Unit = currentScope += 1
  def exitScope(): Unit = currentScope -= 1
  def tabs(): String = List.fill(currentScope)("\t").mkString
  def prepended(str: String): String = tabs() + str
  def outputNoTab(str: String): Unit = {
    internalBuilder.append(str)
  }
  def outputWithTab(str: String): Unit = {
    internalBuilder.append(prepended(str))
  }
}


