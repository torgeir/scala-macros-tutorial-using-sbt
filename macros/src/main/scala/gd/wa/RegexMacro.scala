package gd.wa

import scala.reflect.macros.Context
import language.experimental.macros
import scala.util.matching.Regex

object RegexMacro {

  def regex(str: String): Regex = macro regexImpl

  def regexImpl(c: Context)
               (str: c.Tree) = {

    import c.universe._

    /** prints ast for expression */
    def debug(expr: c.Tree) = println(showRaw(expr))

    debug(str)

    /** destructure the tree to get the string */
    val Literal(Constant(string: String)) = str

    /** create a regex compile time */
    string.r

    /** return the ast creating the regex */
    q"$string.r"
  }

}