package gd.wa

import scala.reflect.macros.Context
import language.experimental.macros
import scala.util.matching.Regex

object NamedRegexMacro {

  def namedRegex(string: String,
                 regex: String,
                 groupName1: String,
                 groupName2: String) = macro namedRegexImpl

  def namedRegexImpl(c: Context)
                    (string: c.Tree,
                     regex: c.Tree,
                     groupName1: c.Tree,
                     groupName2: c.Tree) = {

    import c.universe._

    /** prints ast for expression */
    def debug(expr: Any) = println(showRaw(expr))

    val Literal(Constant(literalString: String)) = string
    val Literal(Constant(literalRegex: String)) = regex
    val Literal(Constant(literalGroupName1: String)) = groupName1
    val Literal(Constant(literalGroupName2: String)) = groupName2

    val pattern = new Regex(literalRegex, literalGroupName1, literalGroupName2)
    val result = pattern.findFirstMatchIn(literalString).get

    val optionalGroupOne = result.group(literalGroupName1)
    val optionalGroupTwo = result.group(literalGroupName2)

    /**
     * Quasiquote examples:
     *   https://gist.github.com/xeno-by/5967900
     *   http://stackoverflow.com/questions/18480707/method-cannot-be-accessed-in-macro-generated-class/18485004#18485004
     */

    // Return anonymous class extending Result, so members are visible
    //   https://issues.scala-lang.org/browse/SI-6992
    q"""
      class Result {
        def ${TermName(literalGroupName1)} = Option($optionalGroupOne)
        def ${TermName(literalGroupName2)} = Option($optionalGroupTwo)
      }
      new Result {}
    """
  }
}