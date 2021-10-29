package parse

import org.antlr.v4.runtime.ParserRuleContext
import org.antlr.v4.runtime.tree.{ErrorNode, ParseTree, ParseTreeListener, RuleNode, TerminalNode, TerminalNodeImpl}
import parse.antlr.Java8Parser
import parse.antlr.Java8Parser._

class JavaParseTreeWalker(private val listener: ParseTreeListener) {

  // [ CUSTOM WALK TEMPLATE SIGNATURE ]
  // private def customWalk(implicit children: List[ParseTree]): Unit =
  // [ CUSTOM WALK FUNCTION CALL ] (notice the lack of parens.
  // customWalk

  private def doForUpdateWalk(implicit children: List[ParseTree]): Unit = {
    children foreach {
      case _: ForUpdateContext => ()
      case child: Any => walk(child)
    }
    walk(children.filter(f => f.isInstanceOf[ForUpdateContext]).head)
  }

  private def doDefaultWalk(implicit children: List[ParseTree]): Unit = {
    children foreach { child => walk(child) }
  }

  def walk(tree: ParseTree): Unit = tree match {
    case e: ErrorNode => listener.visitErrorNode(e)
    case t: TerminalNode => listener.visitTerminal(t)
    case _ =>
      val rule = tree.asInstanceOf[RuleNode]
      implicit val children = (for(i <- 0 until rule.getChildCount) yield rule.getChild(i)).toList
      enterRule(rule)
      rule.getRuleContext match {
        case _ @ (_: BasicForStatementContext | _: BasicForStatementNoShortIfContext)  =>
          doForUpdateWalk
        case _ =>
          doDefaultWalk
      }
      exitRule(rule)
  }

  /**
   * The discovery of a rule node, involves sending two events: the generic
   * {@link ParseTreeListener# enterEveryRule} and a
   * {@link RuleContext}-specific event. First we trigger the generic and then
   * the rule specific. We to them in reverse order upon finishing the node.
   */
  protected def enterRule(r: RuleNode): Unit = {
    val ctx = r.getRuleContext.asInstanceOf[ParserRuleContext]
    listener.enterEveryRule(ctx)
    ctx.enterRule(listener)
  }

  protected def exitRule(r: RuleNode): Unit = {
    val ctx = r.getRuleContext.asInstanceOf[ParserRuleContext]
    ctx.exitRule(listener)
    listener.exitEveryRule(ctx)
  }

}