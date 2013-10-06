package gd.wa

import scala.reflect.macros.Context
import language.experimental.macros
import scala.util.matching.Regex

object Macros {

  def regexImpl(c: Context)
               (str: c.Expr[String]) = {

    import c.universe._

    println(showRaw(str))

    str match {
      case Expr(Literal(Constant(string: String))) =>
        string.r
        reify { str.splice.r }
    }
  }

  def regex(str: String): Regex = macro regexImpl
}