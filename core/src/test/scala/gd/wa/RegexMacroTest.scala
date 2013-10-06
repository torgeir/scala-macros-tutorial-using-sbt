package gd.wa

import org.scalatest.FunSuite
import scala.util.matching.Regex

class RegexMacroTest extends FunSuite {

  import RegexMacro._

  test ("verifies regexes compile time") {
    //regex("[a-z") // Does not compile
    /**
     * [error] ... core/src/test/scala/gd/wa/MacroTest.scala:11: exception during macro expansion:
     * [error] java.util.regex.PatternSyntaxException: Unclosed character class near index 3
     * [error] [a-z
     * [error]    ^
     * [error] 	at java.util.regex.Pattern.error(Pattern.java:1924)
     * [error] 	at java.util.regex.Pattern.clazz(Pattern.java:2493)
     */
  }

  test ("compiles working regex") {
    assertMatchesRegex(regex("([a])"),   "a")
    assertMatchesRegex(regex("([1-2])"), "2")
  }

  def assertMatchesRegex(Pattern: Regex, string: String) =
    assert(string match {
      case Pattern(c) => true
      case _ => false})
}
